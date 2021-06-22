import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import project.db.BookingDao;
import project.db.DBManager;
import project.db.NotificationDao;
import project.db.entity.BookingRooms;
import project.db.entity.Notification;

import java.sql.*;
import java.util.List;

public class BookingDaoTest {

    BookingDao bookingDao;
    NotificationDao notificationDao;

    @Before
    public void setUp(){
        bookingDao = new BookingDao();
        bookingDao.createBookedRoom(1,0, new Date(2133).toLocalDate(), new Date(31232).toLocalDate());

        BookingRooms bookingRooms =
                bookingDao.findBookingRecordByUserIdAndRoomId(0,1);

        notificationDao = new NotificationDao();
        notificationDao.createNotification(0,bookingRooms.getId(),"text");

    }

    @Test
    public void test1() throws SQLException {
        List<Notification> notificationList = notificationDao.findNotificationsByUserId(0L);
        Assert.assertEquals(notificationList.size(), 1);

        bookingDao.makePaidByNotificationId(notificationList.get(0).getId());

        Connection con = DBManager.getInstance().getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT hotel.booked_rooms.is_paid " +
                "FROM booked_rooms WHERE user_id = 0");
        if(rs.next())
            Assert.assertEquals(rs.getInt("is_paid"), 1);
        con.close();
    }


    @After
    public void delete(){

        for (Notification not : notificationDao.findNotificationsByUserId(0L))
            notificationDao.deleteNotificationById(not.getId());

        for (BookingRooms br : bookingDao.findAllBookingRecords())
            bookingDao.editBookingRecordsStatus(br.getId(), 0);

        bookingDao.deleteFreeReservation();
    }

}
