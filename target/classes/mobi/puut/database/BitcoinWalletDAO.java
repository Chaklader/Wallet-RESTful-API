package mobi.puut.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mobi.puut.entities.Status;

/**
 * Created by Chaklader on 6/13/17.
 */

@Repository
@Component("bitcoinWwallet")
public class BitcoinWalletDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Session session() {
        return sessionFactory.getCurrentSession();
    }

    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    public void setDataSource(DataSource jdbc) {

        this.jdbc = new NamedParameterJdbcTemplate(jdbc);
    }

    /**
     * @param status wallet status such as balance, address, transaction history and id of the user
     * @return true if the creation of the status is successful, otherwise, return false.
     */
    public boolean saveOrUpdate(Status status) {

        try {

            Session session = this.sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(status);

            tx.commit();
            session.close();

            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * @param status
     * @return
     */
    public void create(List<Status> status) {

        try {
            Session session = this.sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            for (Status status2 : status) {
                session.save(status2);
            }
            tx.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param id id Id of the bitcoin wallet status
     * @return true if the deletation of the status with Id is successful, otherwsie, return false
     */
//    public boolean delete(int id) {
//
//        Query query = session().createQuery("delete from status where id=:id");
//        query.setInteger("id", id);
//        return query.executeUpdate() == 1;
//    }

    /**
     * @return get the list of all the wallet statuses with the corresponding financial informations
     */
    @SuppressWarnings("unchecked")
    public List<Status> getAllWalletStatus() {

        Session session = this.sessionFactory.openSession();
        String str = "from status";

        List<Status> statusList = session.createQuery(str).list();
        session.close();

        return statusList;

//        return jdbc.query("select * from status", new RowMapper<Status>() {
//
//            public Status mapRow(ResultSet rs, int rowNum) throws SQLException {
//
//                Status status = new Status();
//
//                status.setId(rs.getInt("id"));
//                status.setAddress(rs.getString("address"));
//                status.setBalance(rs.getFloat("balance"));
//                status.setTransaction(rs.getString("transaction"));
//                status.setUser_id(rs.getInt("user_id"));
//
//                return status;
//            }
//        });
    }


    /**
     * @param id Id of the bitcoin wallet status
     * @return the bitcoin wallet status with the corresponding financial information's
     */
    public Status getStatusById(int id) {

        try {
            Session session = this.sessionFactory.openSession();
            Status status = session.get(Status.class, id);
            session.close();
            return status;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }
}
