package project.db;

import project.db.entity.Offer;
import project.db.entity.RequestWish;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OffersDao {

    public static final String SQL__CREATE_OFFER =
            "INSERT INTO offers(user_id, room_id, time_in, time_out) " +
                    "VALUE (?,?,?,?)";

    public static final String SQL__DELETE_OFFER_BY_USER_ID =
            "DELETE FROM offers WHERE user_id = ?";

    public static final String SQL__FIND_OFFERS_BY_USER_ID =
            "SELECT * FROM offers WHERE user_id = ?";


    /**
     * Creates an offer record in the database
     *
     * @param userId user id if database
     * @param roomId room id if database
     */
    public void createOffer(long userId, long roomId, LocalDate time_in, LocalDate time_out) {
        PreparedStatement prStmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            prStmt = con.prepareStatement(SQL__CREATE_OFFER);
            int indexValue = 1;
            prStmt.setLong(indexValue++, userId);
            prStmt.setLong(indexValue++, roomId);
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
     * Removes notification
     *
     * @param userId user id if database
     */

    public void deleteOffersByUserId(long userId) {
        PreparedStatement prStmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            prStmt = con.prepareStatement(SQL__DELETE_OFFER_BY_USER_ID);
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
     * @param userId user number in database
     * @return count of user notification
     */
    public List<Offer> findOffersByUserId(long userId) {
        List<Offer> offersList = new ArrayList<>();
        PreparedStatement prStmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            OfferMapper mapper = new OfferMapper();
            prStmt = con.prepareStatement(SQL__FIND_OFFERS_BY_USER_ID);
            prStmt.setLong(1, userId);
            ResultSet rs = prStmt.executeQuery();
            while (rs.next())
                offersList.add(mapper.mapRow(rs));
            rs.close();
            prStmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return offersList;
    }

    /**
     * Extracts a category from the result set row.
     */
    private static class OfferMapper implements EntityMapper<Offer> {

        @Override
        public Offer mapRow(ResultSet rs) {
            try {
                return new Offer.Builder()
                        .withId(rs.getLong(Fields.BOOKING_REQUEST__BOOKED_ID))
                        .withUserId(rs.getLong(Fields.OFFER__USER_ID))
                        .withRoomId(rs.getLong(Fields.OFFER__ROOM_ID))
                        .withTimeIn(rs.getDate(Fields.OFFER__TIME_IN).toLocalDate())
                        .withTimeOut(rs.getDate(Fields.OFFER__TIME_OUT).toLocalDate())
                        .build();
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }

}
