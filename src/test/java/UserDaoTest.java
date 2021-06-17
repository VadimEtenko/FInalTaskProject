import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import project.db.UserDao;
import project.db.entity.User;

public class UserDaoTest {

    User user;

    @Before
    public void setUp(){
        user = new UserDao().findUserByEmail("em@mail.com");
    }

    @Test
    public void testGetUser(){

    }
}
