package mobi.puut.database;

import mobi.puut.entities.Status;

import java.util.List;

/**
 * Created by Valeriy Kotenok on 24-Jun-17.
 */
public interface StatusDao {

    Status saveStatus(Status status);

    List<Status> getByWalletId(Long id);
}
