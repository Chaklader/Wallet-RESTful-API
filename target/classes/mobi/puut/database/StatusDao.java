package mobi.puut.database;

import mobi.puut.entities.Status;

import java.util.List;

public interface StatusDao {

    Status saveStatus(final Status status);

    List<Status> getByWalletId(final Long walletId);

    boolean getStatusRetentionInfoByWalletId(Long id);
}
