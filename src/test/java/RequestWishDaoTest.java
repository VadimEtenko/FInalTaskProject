import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import project.db.RequestWishDao;
import project.db.entity.RequestWish;

import java.sql.Date;

public class RequestWishDaoTest {

    RequestWishDao rwd;

    @Before
    public void setUp(){
        rwd = new RequestWishDao();
        rwd.createRequestWish(2,1, 2,
                new Date(2021,6,21).toLocalDate(),
                new Date(2021,6,21).toLocalDate());
    }

    @Test
    public void testRequest(){
        for (RequestWish requestWish : rwd.findAllRequestWish()){
            Assert.assertFalse(rwd.findRequestWishByUserId(0L).contains(requestWish));
        }

        RequestWish rq1 = rwd.findRequestWishById(15L);
        RequestWish rq2 = rwd.findRequestWishByUserId(0L).get(0);

        Assert.assertEquals(rq1.toString(), rq2.toString());
        Assert.assertEquals(rq1.getRoomClass(), rq2.getRoomClass());
        Assert.assertEquals(rq1.getClass_id(), rq2.getClass_id());
        Assert.assertEquals(rq1.getTime_in(), rq2.getTime_in());
        Assert.assertEquals(rq1.getNumber_of_beds(), rq2.getNumber_of_beds());
        Assert.assertEquals(rq1.getTime_out(), rq2.getTime_out());
        Assert.assertEquals(rq1.getTime_in(), rq2.getTime_in());
    }

    @After
    public void delete() {
        rwd.deleteRequestWishByIdUserId(2);
    }
}
