import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import project.db.DBManager;
import project.db.OffersDao;
import project.db.RoomDao;
import project.db.entity.Room;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class RoomDaoTest {

    RoomDao roomDao = new RoomDao();
    List<Room> listTest;

    @Before
    public void setUp() throws SQLException {
        Connection con = DBManager.getInstance().getConnection();
        Statement stmt = con.createStatement();
        stmt.executeUpdate(
                "INSERT INTO hotel.offers(user_id, room_id)\n" +
                        "VALUES (0,2),(0,1)");
        DBManager.getInstance().commitAndClose(con);
    }

    @Test
    public void test1() {
        listTest = roomDao.getOfferedRoomsByUserId(0);
        testGetRoomFromList(0);
        testGetRoomFromList(1);
    }

    @Test
    public void test2(){
        List<Room> freeRooms = roomDao.findFreeRooms(
                new Date(2021,10,1).toLocalDate(),
                new Date(2021,11,1).toLocalDate());
        List<Room> freeRooms2 = roomDao.findAllFreeRoomsByCriteria(0L,2);

        for (int i = 0; i < freeRooms.size(); i++){
            equalsRooms(freeRooms.get(i), freeRooms2.get(i));
        }
    }



    public void testGetRoomFromList(int index) {
        Room roomTest1 = roomDao.findRoomById(index+1);
        Room room1 = listTest.get(index);
        equalsRooms(room1, roomTest1);
    }

    public void equalsRooms(Room room1, Room room2){
        Assert.assertEquals(room1.getId(), room2.getId());
        Assert.assertEquals(room1.getNumber(), room2.getNumber());
        Assert.assertEquals((int)room1.getCost(), (int)room2.getCost());
        Assert.assertEquals(room1.getNumberOfBeds(), room2.getNumberOfBeds());
        Assert.assertEquals(room1.getClass(), room2.getClass());
        Assert.assertEquals(room1.getRoomClass(), room2.getRoomClass());
    }


    @After
    public void delete() {
        new OffersDao().deleteOffersByUserId(0);
    }


}
