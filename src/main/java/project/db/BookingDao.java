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

    public static final String SQL__FIND_BOOKED_BY_USER_ID_AND_BOOKED_ID =
            "SELECT * FROM booked_rooms WHERE user_id = ? AND room_id= ?";

    public static final String SQL_PAY_BY_NOTIFICATION_ID =
            "UPDATE booked_rooms SET booked_rooms.is_paid = true\n" +
                    "WHERE booked_rooms.id IN\n" +
                        "(SELECT booked_id FROM users_notifications\n" +
                    "WHERE id = ?)";

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
            "INSERT INTO booked_rooms(room_id, user_id, status_id, time_in, time_out, time_creating, is_paid)\n" +
                    "VALUE (?,?,?,?,?,CURRENT_DATE,?);";



    public List<BookingRooms> findAllBookingRecords() {
        List<BookingRooms> allBookingRecordsList = new ArrayList<>();
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            BookingMapper mapper = new BookingMapper();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL__FIND_ALL_RESERVATION_ENTRIES);
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
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            BookingMapper mapper = new BookingMapper();
            PreparedStatement prStmt =
                    con.prepareStatement(SQL__FIND_RESERVATION_BY_ID);
            prStmt.setLong(1, id);
            ResultSet rs = prStmt.executeQuery();
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
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            PreparedStatement prStmt =
                    con.prepareStatement(SQL__UPDATE_RESERVATION_BY_ID);
            int indexValue = 1;
            prStmt.setLong(indexValue++, bookedStatusId);
            prStmt.setLong(indexValue, idBooked);
            prStmt.executeUpdate();
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
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            PreparedStatement preStmt =
                    con.prepareStatement(SQL__CREATE_NEW_BOOKING_RECORDS);
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

    public BookingRooms findBookingRecordsByUserIdAndRoomId(long userId, long roomId){
        BookingRooms bookingRecordst = new BookingRooms();
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            BookingMapper mapper = new BookingMapper();
            PreparedStatement prStmt =
                    con.prepareStatement(SQL__FIND_BOOKED_BY_USER_ID_AND_BOOKED_ID);
            prStmt.setLong(1, userId);
            prStmt.setLong(2, roomId);
            ResultSet rs = prStmt.executeQuery();
            if (rs.next())
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

    public void makePaidByNotificationId(long notificationId){
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            PreparedStatement preStmt = con.prepareStatement(SQL_PAY_BY_NOTIFICATION_ID);
            preStmt.setLong(1, notificationId);
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
                return new BookingRooms.Builder()
                        .withId(rs.getLong(Fields.ENTITY__ID))
                        .withRoomNumber(rs.getInt(Fields.ROOM__NUMBER))
                        .withUserLogin(rs.getString(Fields.USER__LOGIN))
                        .withStatus(rs.getInt(Fields.BOOKED_ROOM_STATUS))
                        .build();
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}