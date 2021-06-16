package project.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OffersDao {

    public static final String SQL__CREATE_OFFER =
            "INSERT INTO offers(user_id, room_id) " +
                    "VALUE (?,?)";

    public static final String SQL__DELETE_OFFER_BY_USER_ID =
            "DELETE FROM offers WHERE user_id = ?";

    public void createOffer(long user_id, long room_id){
        PreparedStatement prStmt = null;
        Connection con = null;
        try{
            con = DBManager.getInstance().getConnection();
            prStmt = con.prepareStatement(SQL__CREATE_OFFER);
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

    public void deleteOffersByUserId(long user_id){
        PreparedStatement prStmt = null;
        Connection con = null;
        try{
            con = DBManager.getInstance().getConnection();
            prStmt = con.prepareStatement(SQL__DELETE_OFFER_BY_USER_ID);
            prStmt.setLong(1, user_id);
            prStmt.executeUpdate();
            prStmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

}
