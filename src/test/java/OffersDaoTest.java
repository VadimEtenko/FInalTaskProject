import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import project.db.OffersDao;

import java.sql.Date;
import java.time.LocalDate;


public class OffersDaoTest {

    OffersDao offersDao;

    @Before
    public void setUp() {
        LocalDate testDate1 = new Date(2021,11,11).toLocalDate();
        LocalDate testDate2 = new Date(2021,11,12).toLocalDate();
        offersDao = new OffersDao();
        offersDao.createOffer(0,1,testDate1, testDate2);
        offersDao.createOffer(0,2,testDate1, testDate2);
        offersDao.createOffer(0,3, testDate1, testDate2);
    }

    @Test
    public void test1(){
        Assert.assertEquals(offersDao.findOffersByUserId(0).size(),3);
    }

    @After
    public void delete(){
        offersDao.deleteOffersByUserId(0);
    }
}
