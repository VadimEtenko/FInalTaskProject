import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import project.db.RoomDao;
import project.db.entity.Room;

public class RoomDaoTest {

    Room roomById;
    int number;
    RoomDao roomDao = new RoomDao();
    @Before
    public void setUp(){

        roomById = roomDao.findRoomById(1);
        number = roomDao.findRoomNumberById(1);
    }

    @Test
    public void roomDaoTest(){
        Assert.assertEquals(number, roomById.getNumber());
    }
}
