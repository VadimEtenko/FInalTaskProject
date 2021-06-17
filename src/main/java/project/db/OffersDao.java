package project.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OffersDao {

    public static final String SQL__CREATE_OFFER =
            "INSERT INTO offers(user_id, room_id) " +
                    "VALUE (?,?)";

    public static final String SQL__DELETE_OFFER_BY_USER_ID =
            "DELETE FROM offers WHERE user_id = ?";

    public static final String SQL__FIND_OFFERS_BY_USER_ID =
            "SELECT * FROM offers WHERE user_id = ?";


    /**
     * Creates an offer record in the database
     *
     * @param userId
     *      user id if database
     * @param roomId
     *      room id if database
     */
    public void createOffer(long userId, long roomId){
        PreparedStatement prStmt = null;
        Connection con = null;
        try{
            con = DBManager.getInstance().getConnection();
            prStmt = con.prepareStatement(SQL__CREATE_OFFER);
            int indexValue = 1;
            prStmt.setLong(indexValue++, userId);
            prStmt.setLong(indexValue, roomId);
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
     * @param userId
     *      user id if database
     */

    public void deleteOffersByUserId(long userId){
        PreparedStatement prStmt = null;
        Connection con = null;
        try{
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
     *
     * @param userId
     *         user number in database
     * @return
     *         count of user notification
     */
    public int findOffersCountByUserId(long userId){
        int count = 0;
        PreparedStatement prStmt = null;
        Connection con = null;
        try{
            con = DBManager.getInstance().getConnection();
            prStmt = con.prepareStatement(SQL__FIND_OFFERS_BY_USER_ID);
            prStmt.setLong(1, userId);
            ResultSet rs = prStmt.executeQuery();
            while(rs.next())
                count++;
            rs.close();
            prStmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return count;
    }

}
