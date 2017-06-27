package mobi.puut.database;

import mobi.puut.entities.WalletInfo;

import java.util.List;

/**
 * Created by Chaklader on 6/23/17.
 */
public interface WalletInfoDao {
    List<WalletInfo> getAllWallets();

    WalletInfo getByName(String walletName);

    WalletInfo create(String walletName, String address);

    WalletInfo getById(Long id);
}
