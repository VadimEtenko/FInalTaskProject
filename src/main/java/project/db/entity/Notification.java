package project.db.entity;

import project.db.RequestedDao;

import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.Locale;

public class Notification extends Entity {

    long userId;
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

    public static String getMessageVerified(User user, RequestedForBooking requested) {
        ResourceBundle rb = ResourceBundle.getBundle("notification",
                new Locale(user.getLocale()));

        StringBuilder sb =
                new StringBuilder(new String(rb.getString("notification.greeting").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        sb.append(" ").append(user.getName()).append(" ").append(user.getSurname()).append("! ");
        sb.append(new String(rb.getString("notification.verified.maintext.part1").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        sb.append(" ").append(requested.getRoomNumber()).append(" ");
        sb.append(new String(rb.getString("notification.verified.maintext.verified").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));;
        return sb.toString();

    }

    public static String getMessageCanceled(User user, RequestedForBooking requested) {
        ResourceBundle rb = ResourceBundle.getBundle("notification",
                new Locale(user.getLocale()));

        StringBuilder sb =
                new StringBuilder(new String(rb.getString("notification.greeting").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        sb.append(" ").append(user.getName()).append(" ").append(user.getSurname()).append("! ");
        sb.append(new String(rb.getString("notification.verified.maintext.part1").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        sb.append(" ").append(requested.getRoomNumber()).append(" ");
        sb.append(new String(rb.getString("notification.verified.maintext.canceled").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));;
        return sb.toString();
    }

}
