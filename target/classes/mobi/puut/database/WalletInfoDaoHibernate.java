package mobi.puut.database;

import mobi.puut.entities.User;
import mobi.puut.entities.WalletInfo;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
     * get the WalletInfo entity with the currency name and the wallet name
     *
     * @param currencyName name of the currency e.g; bitcoin,  ethereum, litecoin, nem, ripple and dash
     * @param walletName   name of the wallet provided by the user
     * @return WalletInfo entity
     */
    @Override
    public WalletInfo getWalletInfoWithCurrencyAndWalletName(String currencyName, String walletName) {

        try (Session session = getSessionFactory().openSession()) {

            Query query = session.createQuery("FROM WalletInfo WHERE currency = :currency AND name = :name");
            query.setParameter("currency", currencyName);
            query.setParameter("name", walletName);

            List<WalletInfo> walletInfos = query.getResultList();
            return walletInfos.isEmpty() ? null : walletInfos.get(0);
        }
    }


    /**
     * @param name    name of the wallet
     * @param address address of the wallet
     * @return return the created WalletInfo object with provided name and address
     */
    @Override
    public WalletInfo create(String name, String address, String currencyName) {

        WalletInfo walletInfo = new WalletInfo();
        walletInfo.setAddress(address);
        walletInfo.setName(name);
        walletInfo.setCurrency(currencyName);

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

//    @Override
//    public void deleteWalletInfoById(Long id) {
//
//        try (Session session = getSessionFactory().getCurrentSession()) {
//
//            Query query = session.createQuery("delete WalletInfo where id = :id");
//            query.setParameter("id", id);
//            int result = query.executeUpdate();
//
//            System.out.println("Result = " + result);
//
//        } catch (HibernateException e) {
//
//            System.out.println("No result");
//            e.printStackTrace();
//        }
//    }

    @Override
    public void deleteWalletInfoById(Long theId) {

        // get the current hibernate session
        Session currentSession = getSessionFactory().getCurrentSession();

        // delete object with primary key
        Query theQuery =
                currentSession.createQuery("delete WalletInfo where id=:walletId");
        theQuery.setParameter("walletId", theId);

        theQuery.executeUpdate();
    }
}
