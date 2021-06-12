package project.db;

import project.db.bean.RequestedForBooking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestedDao {
    private static final String SQL__FIRM_ALL_REQUESTED_ROOMS =
            "SELECT requested_rooms.*,  users.login, hotel_rooms.number\n" +
                    "FROM requested_rooms, hotel_rooms, users WHERE\n" +
                    "    (hotel_rooms.id IN\n" +
                    "        (SELECT requested_rooms.room_id FROM requested_rooms)\n" +
                    "    AND hotel_rooms.id = requested_rooms.room_id)\n" +
                    "    AND (users.id IN\n" +
                    "         (SELECT requested_rooms.user_id FROM requested_rooms)\n" +
                    "    AND  users.id = requested_rooms.user_id)";

    private static final String SQL__FIND_REQUESTED_ROOMS_BY_ROOM_ID =
            "SELECT * FROM requested_rooms WHERE room_id = ?";

    private static final String SQL__DELETE_REQUESTED_ROOMS_BY_ROOM_ID =
            "DELETE FROM requested_rooms WHERE room_id = ?";

    private static final String SQL__CREATE_REQUEST =
            "INSERT INTO requested_rooms(user_id, room_id) " +
                    "VALUE (?,?)";
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
            rs = stmt.executeQuery(SQL__FIRM_ALL_REQUESTED_ROOMS);
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

    public RequestedForBooking findRequestedRoomsById(long id) {
        RequestedForBooking requestedForBooking = null;
        PreparedStatement prStmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            RequestedRoomsMapper mapper = new RequestedRoomsMapper();
            prStmt = con.prepareStatement(SQL__FIND_REQUESTED_ROOMS_BY_ROOM_ID);
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

    public void createRequest(long user_id, long room_id){
        PreparedStatement prStmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            prStmt = con.prepareStatement(SQL__CREATE_REQUEST);
            int indexValue = 1;
            prStmt.setLong(indexValue++, user_id);
            prStmt.setLong(indexValue, room_id);
            prStmt.executeUpdate();
            prStmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    public void deleteRequestedRoomById(Long id){
        PreparedStatement prStmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            prStmt = con.prepareStatement(SQL__DELETE_REQUESTED_ROOMS_BY_ROOM_ID);
            prStmt.setLong(1, id);
            prStmt.executeUpdate();
            prStmt.close();
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
                rfb.setId(rs.getLong(Fields.BOOKING_REQUEST_BEAN__BOOKED_ID));
                try {
                    rfb.setUserLogin(rs.getString(Fields.BOOKING_REQUEST_BEAN__USER_LOGIN_WHO_BOOKED));
                    rfb.setRoomNumber(rs.getString(Fields.BOOKING_REQUEST_BEAN__RESERVED_ROOM_NUMBER));
                }catch(SQLException ignored){}
                rfb.setRoomId(rs.getLong(Fields.BOOKING_REQUEST_BEAN__RESERVED_ROOM_ID));
                rfb.setUserId(rs.getLong(Fields.BOOKING_REQUEST_BEAN__USER_ID_WHO_BOOKED));
                return rfb;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
