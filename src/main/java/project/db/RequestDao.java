package project.db;

import project.db.entity.RequestedForBooking;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RequestDao {
    private static final String SQL__FIND_ALL_REQUESTED_ROOMS =
            "SELECT requested_rooms.*, users.login, hotel_rooms.number\n" +
                    "FROM requested_rooms, hotel_rooms, users\n" +
                    "WHERE (hotel_rooms.id IN\n" +
                        "(SELECT requested_rooms.room_id FROM requested_rooms)\n" +
                        "AND hotel_rooms.id = requested_rooms.room_id)\n" +
                    "AND (users.id IN\n" +
                        "(SELECT requested_rooms.user_id FROM requested_rooms)\n" +
                    "AND users.id = requested_rooms.user_id)";

    private static final String SQL__FIND_REQUESTED_ROOM_BY_ID =
            "SELECT requested_rooms.*,  users.login, hotel_rooms.number\n" +
                    "FROM requested_rooms, hotel_rooms, users " +
                    "WHERE (hotel_rooms.id IN\n" +
                        "(SELECT requested_rooms.room_id FROM requested_rooms)\n" +
                        "AND hotel_rooms.id = requested_rooms.room_id)\n" +
                    "AND (users.id IN\n" +
                        "(SELECT requested_rooms.user_id FROM requested_rooms))\n" +
                    "AND requested_rooms.id = ?;";

    private static final String SQL__FIND_REQUESTED_ROOMS_BY_USER_ID =
            "SELECT requested_rooms.*,  users.login, hotel_rooms.number\n" +
                    "FROM requested_rooms, hotel_rooms, users " +
                    "WHERE (hotel_rooms.id IN\n" +
                        "(SELECT requested_rooms.room_id FROM requested_rooms)\n" +
                        "AND hotel_rooms.id = requested_rooms.room_id)\n" +
                    "AND (users.id IN\n" +
                        "(SELECT requested_rooms.user_id FROM requested_rooms))\n" +
                    "AND requested_rooms.user_id = ?";

    private static final String SQL__FIND_REQUESTED_ROOMS_BY_USER_ID_AND_ROOM_NUMBER =
            "SELECT requested_rooms.*,  users.login, hotel_rooms.number\n" +
                    "FROM requested_rooms, hotel_rooms, users " +
                    "WHERE (hotel_rooms.id IN\n" +
                         "(SELECT requested_rooms.room_id FROM requested_rooms)\n" +
                        "AND hotel_rooms.id = requested_rooms.room_id)\n" +
                    "AND (users.id IN\n" +
                        "(SELECT requested_rooms.user_id FROM requested_rooms))\n" +
                    "AND requested_rooms.user_id = ?\n" +
                    "AND hotel_rooms.number = ?";

    private static final String SQL__DELETE_REQUESTED_ROOMS_BY_ROOM_ID =
            "DELETE FROM requested_rooms WHERE room_id = ?";

    private static final String SQL__CREATE_REQUEST =
            "INSERT INTO requested_rooms(user_id, room_id, time_in, time_out) " +
                    "VALUE (?,?,?,?)";

    public static final String SQL__DELETE_REQUESTED_BY_ID =
            "DELETE FROM requested_rooms WHERE id = ?";

    /**
     * Returns all applications for booking rooms.
     *
     * @return List of applications for booking rooms entities.
     */
    public List<RequestedForBooking> findAllRequestedRooms() {
        List<RequestedForBooking> requestedForBookingList = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            RequestedRoomsMapper mapper = new RequestedRoomsMapper();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL__FIND_ALL_REQUESTED_ROOMS);
            while (rs.next())
                requestedForBookingList.add(mapper.mapRow(rs));
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return requestedForBookingList;
    }

    public List<RequestedForBooking> findRequestedRoomsByUserId(long id) {
        List<RequestedForBooking> requestedForBookingList = new ArrayList<>();
        PreparedStatement prStmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            RequestedRoomsMapper mapper = new RequestedRoomsMapper();
            prStmt = con.prepareStatement(SQL__FIND_REQUESTED_ROOMS_BY_USER_ID);
            prStmt.setLong(1, id);
            rs = prStmt.executeQuery();
            while (rs.next())
                requestedForBookingList.add(mapper.mapRow(rs));
            rs.close();
            prStmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return requestedForBookingList;
    }

    public boolean isCreatedRequestRoomByUserIdAndRoomNumber(long id, long roomNumber) {
        PreparedStatement prStmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean isCreated = true;
        try {
            con = DBManager.getInstance().getConnection();
            RequestedRoomsMapper mapper = new RequestedRoomsMapper();
            prStmt = con.prepareStatement(SQL__FIND_REQUESTED_ROOMS_BY_USER_ID_AND_ROOM_NUMBER);
            int indexValue = 1;
            prStmt.setLong(indexValue++, id);
            prStmt.setLong(indexValue, roomNumber);
            rs = prStmt.executeQuery();
            if (rs.next())
                isCreated = false;
            rs.close();
            prStmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return isCreated;
    }

    public RequestedForBooking findRequestedRoomById(long id) {
        RequestedForBooking requestedForBooking = null;
        PreparedStatement prStmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            RequestedRoomsMapper mapper = new RequestedRoomsMapper();
            prStmt = con.prepareStatement(SQL__FIND_REQUESTED_ROOM_BY_ID);
            prStmt.setLong(1, id);
            rs = prStmt.executeQuery();
            if (rs.next())
                requestedForBooking = mapper.mapRow(rs);
            rs.close();
            prStmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return requestedForBooking;
    }

    public void createRequest(long user_id, long room_id, LocalDate time_in, LocalDate time_out) {
        PreparedStatement prStmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            prStmt = con.prepareStatement(SQL__CREATE_REQUEST);
            int indexValue = 1;
            prStmt.setLong(indexValue++, user_id);
            prStmt.setLong(indexValue++, room_id);
            prStmt.setDate(indexValue++, Date.valueOf(time_in));
            prStmt.setDate(indexValue, Date.valueOf(time_out));
            prStmt.executeUpdate();
            prStmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    public void deleteRequestedByRoomNumber(int roomId) {
        PreparedStatement prStmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            prStmt = con.prepareStatement(SQL__DELETE_REQUESTED_ROOMS_BY_ROOM_ID);
            prStmt.setInt(1, roomId);
            prStmt.executeUpdate();
            prStmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    public void deleteRequestById(long id) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = DBManager.getInstance().getConnection();
            preparedStatement = con.prepareStatement(SQL__DELETE_REQUESTED_BY_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    /**
     * Extracts a category from the result set row.
     */
    private static class RequestedRoomsMapper implements EntityMapper<RequestedForBooking> {

        @Override
        public RequestedForBooking mapRow(ResultSet rs) {
            try {
                RequestedForBooking rfb = new RequestedForBooking();
                rfb.setId(rs.getLong(Fields.BOOKING_REQUEST__BOOKED_ID));
                try {
                    rfb.setUserLogin(rs.getString(Fields.BOOKING_REQUEST__USER_LOGIN_WHO_BOOKED));
                    rfb.setRoomNumber(rs.getInt(Fields.BOOKING_REQUEST__RESERVED_ROOM_NUMBER));
                } catch (SQLException ignored) {
                }
                rfb.setRoomId(rs.getLong(Fields.BOOKING_REQUEST__RESERVED_ROOM_ID));
                rfb.setUserId(rs.getLong(Fields.BOOKING_REQUEST__USER_ID_WHO_BOOKED));
                rfb.setTimeIn(rs.getDate(Fields.BOOKING_REQUEST__RESERVED_ROOM_TIME_IN).toLocalDate());
                rfb.setTimeOut(rs.getDate(Fields.BOOKING_REQUEST__RESERVED_ROOM_TIME_OUT).toLocalDate());
                return rfb;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
