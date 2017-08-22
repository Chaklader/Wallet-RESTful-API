package mobi.puut.database;

import mobi.puut.entities.Status;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Created by Chaklader on 6/24/17.
 */
@Repository
public class StatusDaoImpl implements StatusDao {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * @param status takes transaction status save it in the status database table
     * @return return the status object
     */
    @Transactional(rollbackFor = Exception.class)
    public Status saveStatus(final Status status) {
        sessionFactory.getCurrentSession().persist(status);
        return status;
    }

    /**
     * @param walletId takes wallet ID as the input paramenter
     * @return return the list of the statuses
     */
    @Transactional(rollbackFor = Exception.class)
    public List<Status> getByWalletId(final Long walletId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Status where wallet_id = :walletId")
                .setParameter("walletId", walletId).getResultList();
    }

    // get the status with the wallet Id
    @Transactional(rollbackFor = Exception.class)
    public boolean getStatusRetentionInfoByWalletId(Long id) {

        List<Status> statuses = sessionFactory.getCurrentSession()
                .createQuery("from Status where wallet_id = :id")
                .setParameter("id", id)
                .getResultList();

        return Objects.isNull(statuses) || statuses.isEmpty() ? false : true;
    }
}
