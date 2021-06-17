package project.db.entity;

import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.Locale;

/**
 *  The entity that users will receive
 *  when reviewing their applications
 *
 * @author V. Etenko
 *
 */

public class Notification extends Entity {

    long userId;
    long bookedId;
    String text;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getBookedId() {
        return bookedId;
    }

    public void setBookedId(long bookedId) {
        this.bookedId = bookedId;
    }


    /**
     * @param user
     *          the entity of the user who will receive the message
     * @param roomNumber
     *          the number of the room the user wanted to book
     * @return Application approval message
     */

    public static String getMessageVerified(User user, int roomNumber) {
        ResourceBundle rb = ResourceBundle.getBundle("notification",
                new Locale(user.getLocale()));

        StringBuilder sb =
                new StringBuilder(new String(rb.getString("notification.greeting").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        sb.append(" ").append(user.getName()).append(" ").append(user.getSurname()).append("! ");
        sb.append(new String(rb.getString("notification.verified.maintext.part1").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        sb.append(" ").append(roomNumber).append(" ");
        sb.append(new String(rb.getString("notification.verified.maintext.verified").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        return sb.toString();
    }


    /**
     * @param user
     *          the entity of the user who will receive the message
     * @param roomNumber
     *          the number of the room the user wanted to book
     * @return Application rejection message
     */

    public static String getMessageCanceled(User user, int roomNumber) {
        ResourceBundle rb = ResourceBundle.getBundle("notification",
                new Locale(user.getLocale()));

        StringBuilder sb =
                new StringBuilder(new String(rb.getString("notification.greeting").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        sb.append(" ").append(user.getName()).append(" ").append(user.getSurname()).append("! ");
        sb.append(new String(rb.getString("notification.verified.maintext.part1").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        sb.append(" ").append(roomNumber).append(" ");
        sb.append(new String(rb.getString("notification.verified.maintext.canceled").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));;
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Notification[" +
                "userId=" + userId +
                ", bookedId=" + bookedId +
                ", text='" + text + '\'' +
                ']';
    }

    /**
     * Builder for this class
     */

    public static class Builder implements BuilderInterface{

        private Notification notification;

        public Builder() {
            notification = new Notification();
        }

        public Builder withUserId(long userId){
            notification.userId = userId;
            return this;
        }

        public Builder withBookedId(long bookedId){
            notification.bookedId = bookedId;
            return this;
        }

        public Builder withText(String text){
            notification.text = text;
            return this;
        }

        public Builder withId(Long id) {
            notification.setId(id);
            return this;
        }

        public Notification build(){
            return notification;
        }

    }

}
