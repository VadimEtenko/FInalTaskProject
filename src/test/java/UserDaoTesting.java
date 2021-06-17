import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import project.db.UserDao;
import project.db.entity.User;

public class UserDaoTesting {

    User userEmail;
    User userLogin;

    @Before
    public void setUp(){
        UserDao userDao = new UserDao();
        userEmail = userDao.findUserByEmail("em@mail.com");
        userLogin = userDao.findUserByLogin("F_H");
    }

    @Test
    public void testGetUserByLoginByEmail(){
        Assert.assertEquals(userEmail.getId(), userLogin.getId());
        Assert.assertEquals(userEmail.getName(), userLogin.getName());
        Assert.assertEquals(userEmail.getSurname(), userLogin.getSurname());
        Assert.assertEquals(userEmail.getLogin(), userLogin.getLogin());
        Assert.assertEquals(userEmail.getRoleId(), userLogin.getRoleId());
        Assert.assertEquals(userEmail.getLocale(), userLogin.getLocale());
        Assert.assertEquals(userEmail.getEmail(), userLogin.getEmail());
    }

}
