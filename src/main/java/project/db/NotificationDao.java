package project.db;


import project.db.entity.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDao {

    public static final String SQL__FIND_NOTIFICATIONS_BY_USER_ID =
            "SELECT * FROM users_notifications WHERE user_id = ?";

    private static final String SQL__CREATE_NOTIFICATION =
            "INSERT INTO users_notifications(user_id, booked_id, text) " +
                    "VALUE (?,?,?)";

    public static final String SQL__DELETE_NOTIFICATION_BY_ID =
            "DELETE FROM users_notifications WHERE id = ?";


    /**
     * @param userId
     *      user id in the database
     * @return List of user's notifications
     */
    public List<Notification> findNotificationsByUserId(Long userId){
        List<Notification> notificationList = new ArrayList<>();
        PreparedStatement prStmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            NotificationRoomsMapper mapper = new NotificationRoomsMapper();
            prStmt = con.prepareStatement(SQL__FIND_NOTIFICATIONS_BY_USER_ID);
            prStmt.setLong(1, userId);
            rs = prStmt.executeQuery();
            while (rs.next())
                notificationList.add(mapper.mapRow(rs));
            rs.close();
            prStmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return notificationList;
    }


    /**
     * Creates a message for the user in the database
     *
     * @param userId
     *      user's id in the database
     * @param bookedId
     *      booking record id in the database
     * @param text
     *      The text that will be in the notification
     */
    public void createNotification(long userId, long bookedId, String text){
        PreparedStatement prStmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            prStmt = con.prepareStatement(SQL__CREATE_NOTIFICATION);
            int indexValue = 1;
            prStmt.setLong(indexValue++, userId);
            prStmt.setLong(indexValue++, bookedId);
            prStmt.setString(indexValue, text);
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
     * @param id
     *      notification's number in database
     */

    public void deleteNotificationById(long id){
        PreparedStatement prStmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            NotificationRoomsMapper mapper = new NotificationRoomsMapper();
            prStmt = con.prepareStatement(SQL__DELETE_NOTIFICATION_BY_ID);
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
    private static class NotificationRoomsMapper implements EntityMapper<Notification> {

        @Override
        public Notification mapRow(ResultSet rs) {
            try {

                return new Notification.Builder()
                        .withId(rs.getLong(Fields.ENTITY__ID))
                        .withUserId(rs.getLong(Fields.NOTIFICATION__USER_ID))
                        .withBookedId(rs.getLong(Fields.NOTIFICATION__BOOKED_ID))
                        .withText(rs.getString(Fields.NOTIFICATION__TEXT))
                        .build();
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
