package project.db;

import project.db.entity.BookingRooms;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDao {

    private static final String SQL__FIND_ALL_RESERVATION_ENTRIES =
            "SELECT booked_rooms.id,  users.login, hotel_rooms.number, booked_rooms.status_id\n" +
                    "FROM booked_rooms, hotel_rooms, users WHERE\n" +
                    "(hotel_rooms.id IN\n" +
                    "(SELECT booked_rooms.room_id FROM booked_rooms)\n" +
                    "AND hotel_rooms.id = booked_rooms.room_id)\n" +
                    "AND (users.id IN\n" +
                    "(SELECT booked_rooms.user_id FROM booked_rooms)\n" +
                    "AND  users.id = booked_rooms.user_id)";

    public static final String SQL__UPDATE_RESERVATION_BY_ID =
            "UPDATE booked_rooms\n" +
                    "    SET status_id = ?\n" +
                    "    WHERE id = ?";

    public List<BookingRooms> findAllBookingRecords() {
        List<BookingRooms> allBookingRecordsList = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            BookingMapper mapper = new BookingMapper();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL__FIND_ALL_RESERVATION_ENTRIES);
            while (rs.next())
                allBookingRecordsList.add(mapper.mapRow(rs));
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return allBookingRecordsList;
    }



    public void editBookingRecords(long idBooked, long bookedStatusId) {
        PreparedStatement stmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            stmt = con.prepareStatement(SQL__FIND_ALL_RESERVATION_ENTRIES);
            int indexValue = 1;
            stmt.setLong(indexValue++, idBooked);
            stmt.setLong(indexValue, bookedStatusId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }



    private static class BookingMapper implements EntityMapper<BookingRooms> {

        @Override
        public BookingRooms mapRow(ResultSet rs) {
            try {
                BookingRooms bookingRooms = new BookingRooms();
                bookingRooms.setId(rs.getLong(Fields.ENTITY__ID));
                bookingRooms.setRoomNumber(rs.getInt(Fields.ROOM__NUMBER));
                bookingRooms.setUserLogin(rs.getString(Fields.USER__LOGIN));
                bookingRooms.setStatus(rs.getInt(Fields.BOOKED_ROOM_STATUS));;
                return bookingRooms;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}