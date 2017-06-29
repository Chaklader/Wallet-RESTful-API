package mobi.puut.database;

import mobi.puut.entities.Status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Chaklader on 6/13/17.
 */

@ActiveProfiles("dev")
@ContextConfiguration(locations = {
        "../config/database-context.xml",
        "classpath:mobi/puut/config/datasource.xml",
//        "classpath:com/puut/bitcoin/config/security-context.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class BitcoinWalletDAOTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private BitcoinWalletDAO bitcoinWalletDAO;

    @Before
    public void init() {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);

        jdbc.execute("delete from status");
    }

    @Test
    public void testCreateStatus() {

        Status status = new Status(12.45f, "received 12.45 BTC", "saf343dsdf34343fdfdsfds11ff", 1);
        assertTrue("Status creation should return true", bitcoinWalletDAO.saveOrUpdate(status));


        List<Status> statuses = bitcoinWalletDAO.getAllWalletStatus();

        assertEquals("should return 1 as the total size of the statuses", 1, statuses.size());

        assertEquals("retrived status balance should match with the created status",
                statuses.get(0).getBalance(), status.getBalance(), 0.001f);

        assertEquals("retrived status transaction should match with the created status",
                statuses.get(0).getTransaction(), status.getTransaction());

        assertEquals("retrived status address should match with the created status",
                statuses.get(0).getAddress(), status.getAddress());

        assertEquals("retrived user Id of the status should match with the created status",
                statuses.get(0).getUser_id(), status.getUser_id());

        status = statuses.get(0);

        float balance = 12.45f;
        status.setBalance(balance);

        assertTrue("The updated balance should return true", bitcoinWalletDAO.saveOrUpdate(status));
        assertEquals("Should match with the updated status", balance, status.getBalance(), 0.001f);

        String transaction = "received 12.45 BTC";
        status.setTransaction(transaction);
        assertTrue("The updated transaction should return true", bitcoinWalletDAO.saveOrUpdate(status));
        assertEquals("Should match with the updated status", transaction, status.getTransaction());

        String address = "sdasda34343wwwdfdfdg121212";
        status.setAddress(address);

        assertTrue("The updated status should return true", bitcoinWalletDAO.saveOrUpdate(status));
        assertEquals("Should match with the updated status", address, status.getAddress());

        int userId = 2;
        status.setUser_id(userId);

        assertTrue("The updated user id should return true", bitcoinWalletDAO.saveOrUpdate(status));
        assertEquals("Should match with the updated status", userId, status.getUser_id());

//        assertTrue("The successful deletation should return true", bitcoinWalletDAO.delete(status.getId()));
//
//        List<Status> emptyStatuses = bitcoinWalletDAO.getAllWalletStatus();
//        assertEquals("The size of the statuses should be zero after the deletation", 0, emptyStatuses.size());
    }
}