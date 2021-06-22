import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import project.db.RequestDao;
import project.db.UserDao;
import project.db.entity.RequestedForBooking;
import project.db.entity.Role;
import project.db.entity.User;

import java.sql.Date;
import java.util.List;

public class UserDaoTesting {


    UserDao userDao;
    RequestDao requestDao;

    @Before
    public void setUp(){
        userDao = new UserDao();
        requestDao = new RequestDao();

        requestDao.createRequest(0,1,
                new Date(2021,10,6).toLocalDate(),
                new Date(2021,10,6).toLocalDate());
    }

    @Test
    public void testGetUserByLoginByEmail(){
        equalsUsers(userDao.findUserByEmail("email@mail.com"),
                userDao.findUserByLogin("Ivan228"));
    }

    @Test
    public void testGetUserByReRequest(){
        List<RequestedForBooking> requestsRoom = requestDao.findAllRequestedRooms();
        RequestedForBooking rdb = null;
        for(RequestedForBooking rfb : requestsRoom)
            if (rfb.getRoomNumber() == 1) {
                rdb = rfb;
                break;
            }

        equalsUsers(userDao.findUserByRequestedId(rdb.getId()),
            userDao.findUsersByRequestedRoomId(1).get(0));
    }

    @Test
    public void test3(){
        User user1 = new User.Builder()
                .withName("Name")
                .withSurname("Surname")
                .withLogin("Login")
                .withEmail("email")
                .withPassword("1111")
                .withLocale("ru").
                        build();
        userDao.createNewUser(user1);
        User user2 = userDao.findUserByLogin("Login");
        Assert.assertEquals(Role.getRole(user1), Role.getRole(user2));

        equalsUsers( user1, user2);
    }

    public void equalsUsers(User user1, User user2){
        Assert.assertEquals(user1.getName(), user2.getName());
        Assert.assertEquals(user1.getSurname(), user2.getSurname());
        Assert.assertEquals(user1.getLogin(), user2.getLogin());
        Assert.assertEquals(user1.getRoleId(), user2.getRoleId());
        Assert.assertEquals(user1.getLocale(), user2.getLocale());
        Assert.assertEquals(user1.getEmail(), user2.getEmail());
    }

    @After
    public void delete() {
        requestDao.deleteRequestedByRoomNumber(1);
        userDao.deleteUserByLogin("Login");
    }

}
