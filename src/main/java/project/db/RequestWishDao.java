package project.db;

import project.db.entity.RequestWish;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RequestWishDao {
    private static final String SQL__CREATE_REQUEST =
            "INSERT INTO request_wish (user_id, class_id, number_of_beds, time_in, time_out) " +
                    "VALUE (?,?,?,?,?)";

    private static final String SQL__FIND_ALL_REQUEST =
            "SELECT request_wish.*, users.login\n" +
                    "FROM request_wish, users\n" +
                    "WHERE (users.id IN\n" +
                    "(SELECT request_wish.user_id FROM request_wish))\n" +
                    "AND users.id = request_wish.user_id";

    private static final String SQL__FIND_REQUEST_BY_ID =
            "SELECT request_wish.*, users.login\n" +
                    "FROM request_wish, users\n" +
                    "WHERE (users.id IN\n" +
                    "(SELECT request_wish.user_id FROM request_wish))\n" +
                    "AND users.id = request_wish.user_id\n" +
                    " AND request_wish.id = ?";

    private static final String SQL__FIND_REQUEST_BY_USER_ID =
            "SELECT request_wish.*, users.login\n" +
                    "FROM request_wish, users\n" +
                    "WHERE (users.id IN\n" +
                    "(SELECT request_wish.user_id FROM request_wish))\n" +
                    "AND users.id = request_wish.user_id\n" +
                    " AND request_wish.user_id = ?";

    private static final String SQL__DELETE_REQUEST_BY_USER_ID =
            "DELETE FROM request_wish WHERE user_id = ?";


    /**
     * Create request by wishes record on database
     *
     * @param userId
     *      user id in database
     * @param classId
     *      class of rooms id in database
     * @param numberOfBeds
     *      wished number of beds
     * @param time_in
     *      User's desired check-in time
     * @param time_out
     *      Desired check-out time for the user
     */
    public void createRequestWish(long userId, long classId,
                                  int numberOfBeds, LocalDate time_in, LocalDate time_out) {
        PreparedStatement prStmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            prStmt = con.prepareStatement(SQL__CREATE_REQUEST);
            int indexValue = 1;
            prStmt.setLong(indexValue++, userId);
            prStmt.setLong(indexValue++, classId);
            prStmt.setInt(indexValue++, numberOfBeds);
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


    /**
     * @return
     *      List of all entities of request by wishes
     */

    public List<RequestWish> findAllRequestWish() {
        List<RequestWish> requestWish = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            RequestedWishMapper mapper = new RequestedWishMapper();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL__FIND_ALL_REQUEST);
            while (rs.next())
                requestWish.add(mapper.mapRow(rs));
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return requestWish;
    }


    /**
     * Delete request by wishes record in database
     *
     * @param userId
     *      user id in database
     */

    public void deleteRequestWishByIdUserId(long userId) {
        PreparedStatement prStmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            prStmt = con.prepareStatement(SQL__DELETE_REQUEST_BY_USER_ID);
            prStmt.setLong(1, userId);
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
     *
     * @param id
     *      request by wishes id record in database
     * @return
     *      request by wishes entity
     */

    public RequestWish findRequestWishById(Long id) {
        RequestWish requestWish = null;
        PreparedStatement prStmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            RequestedWishMapper mapper = new RequestedWishMapper();
            prStmt = con.prepareStatement(SQL__FIND_REQUEST_BY_ID);
            prStmt.setLong(1, id);
            rs = prStmt.executeQuery();
            if (rs.next())
                requestWish = mapper.mapRow(rs);
            prStmt.close();
            rs.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return requestWish;
    }


    /**
     *
     * @param userId
     *      user id in database
     * @return
     *      list of request by wishes entities
     */

    public List<RequestWish> findRequestWishByUserId(Long userId) {
        List<RequestWish> requestWishList = new ArrayList<>();
        PreparedStatement prStmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            RequestedWishMapper mapper = new RequestedWishMapper();
            prStmt = con.prepareStatement(SQL__FIND_REQUEST_BY_USER_ID);
            prStmt.setLong(1, userId);
            rs = prStmt.executeQuery();
            while (rs.next())
                requestWishList.add(mapper.mapRow(rs));
            prStmt.close();
            rs.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return requestWishList;
    }

    /**
     * Extracts a category from the result set row.
     */
    private static class RequestedWishMapper implements EntityMapper<RequestWish> {

        @Override
        public RequestWish mapRow(ResultSet rs) {
            try {
                RequestWish rw = new RequestWish.Builder()
                        .withId(rs.getLong(Fields.BOOKING_REQUEST__BOOKED_ID))
                        .withUser_id(rs.getLong(Fields.REQUEST_WISHED__USER_ID_WHO_WISHED))
                        .withUser_id(rs.getLong(Fields.REQUEST_WISHED__WISHED_CLASS_ID))
                        .withNumberOfBeds(rs.getInt(Fields.REQUEST_WISHED__WISHED_NUMBER_OF_BEDS))
                        .withTime_in(rs.getDate(Fields.REQUEST_WISHED__WISHED_TIME_IN).toLocalDate())
                        .withTime_out(rs.getDate(Fields.REQUEST_WISHED__WISHED_TIME_OUT).toLocalDate())
                        .build();
                try {
                    rw.setUserNick(rs.getString(Fields.USER__LOGIN));
                } catch (SQLException ignored) {}
                return rw;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
