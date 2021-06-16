package project.db;

import project.db.entity.BookingRooms;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDao {

    private static final String SQL__FIND_ALL_RESERVATION_ENTRIES =
            "SELECT booked_rooms.id,  users.login, hotel_rooms.number, booked_rooms.status_id, booked_rooms.time_in, booked_rooms.time_out\n" +
                    "FROM booked_rooms, hotel_rooms, users WHERE\n" +
                    "(hotel_rooms.id IN\n" +
                    "(SELECT booked_rooms.room_id FROM booked_rooms)\n" +
                    "AND hotel_rooms.id = booked_rooms.room_id)\n" +
                    "AND (users.id IN\n" +
                    "(SELECT booked_rooms.user_id FROM booked_rooms)\n" +
                    "AND  users.id = booked_rooms.user_id)";

    public static final String SQL__UPDATE_RESERVATION_BY_ID =
            "UPDATE booked_rooms SET status_id = ? WHERE id = ?";

    public static final String SQL__FIND_RESERVATION_BY_ID =
            "SELECT booked_rooms.id,  users.login, hotel_rooms.number, booked_rooms.status_id\n" +
                    "FROM booked_rooms, hotel_rooms, users WHERE\n" +
                    "    (hotel_rooms.id IN\n" +
                    "     (SELECT booked_rooms.room_id FROM booked_rooms)\n" +
                    "        AND hotel_rooms.id = booked_rooms.room_id)\n" +
                    "                                        AND (users.id IN\n" +
                    "                                             (SELECT booked_rooms.user_id FROM booked_rooms)\n" +
                    "        AND  users.id = booked_rooms.user_id)\n" +
                    "                                        AND booked_rooms.id = ?";

    public static final String SQL__DELETE_FREE_BOOKED_ROOMS =
            "DELETE FROM booked_rooms WHERE status_id = 0";

    private static final String SQL__CREATE_NEW_BOOKING_RECORDS =
            "INSERT INTO booked_rooms(room_id, user_id, status_id, time_in, time_out, is_paied)\n" +
                    "VALUE (?,?,?,?,?,?);";



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

    public BookingRooms findBookingRecordsById(long id) {
        BookingRooms bookingRecordst = new BookingRooms();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            BookingMapper mapper = new BookingMapper();
            stmt = con.prepareStatement(SQL__FIND_RESERVATION_BY_ID);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            while (rs.next())
                bookingRecordst = mapper.mapRow(rs);
            rs.close();
            con.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return bookingRecordst;
    }



    public void editBookingRecords(long idBooked, long bookedStatusId) {
        PreparedStatement stmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            stmt = con.prepareStatement(SQL__UPDATE_RESERVATION_BY_ID);
            int indexValue = 1;
            stmt.setLong(indexValue++, bookedStatusId);
            stmt.setLong(indexValue, idBooked);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    public void deleteFreeReservation(){
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            con.createStatement().
                    executeUpdate(SQL__DELETE_FREE_BOOKED_ROOMS);
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    public void createBookedRoom(long roomId, long userId){
        PreparedStatement preStmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            preStmt = con.prepareStatement(SQL__CREATE_NEW_BOOKING_RECORDS);
            int indexValue = 1;
            preStmt.setLong(indexValue++, roomId);
            preStmt.setLong(indexValue++, userId);
            preStmt.setInt(indexValue++, 1);
            preStmt.setDate(indexValue++, new Date(3423));
            preStmt.setDate(indexValue++, new Date(23423));
            preStmt.setBoolean(indexValue, false);
            preStmt.executeUpdate();
            preStmt.close();
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