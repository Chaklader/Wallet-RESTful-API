package mobi.puut.database;

import mobi.puut.entities.WalletInfo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Chaklader on 6/24/17.
 */
@Repository
public class WalletInfoDaoHibernate
        extends HibernateDaoSupport
        implements WalletInfoDao {

    @Override
    public List<WalletInfo> getAllWallets() {
        try (Session session = getSessionFactory().openSession()) {
            List<WalletInfo> result = session.createQuery("from WalletInfo").list();
            return result;
        }
    }

    @Override
    public WalletInfo getByName(String walletName) {
        try (Session session = getSessionFactory().openSession()) {
            Query query = session.createQuery("from WalletInfo where name = :name");
            query.setParameter("name", walletName);
            List<WalletInfo> userList = query.list();
            return userList.isEmpty() ? null : userList.get(0);
        }
    }

    @Override
    public WalletInfo getById(final Long id) {
        try (Session session = getSessionFactory().openSession()) {
            WalletInfo walletInfo = session.get(WalletInfo.class, id);
            return walletInfo;
        }
    }


    /**
     *
     * @param name name of the wallet
     * @param address address of the wallet
     * @return return the created WalletInfo object with provided name and address
     */
    @Override
    public WalletInfo create(String name, String address) {
        WalletInfo walletInfo = new WalletInfo();
        walletInfo.setAddress(address);
        walletInfo.setName(name);

        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(walletInfo);
            transaction.commit();
            return walletInfo;
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
