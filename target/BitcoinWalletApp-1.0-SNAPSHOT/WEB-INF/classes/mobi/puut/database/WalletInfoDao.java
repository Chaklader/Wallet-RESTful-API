package mobi.puut.database;

import mobi.puut.entities.WalletInfo;

import java.util.List;

/**
 * Created by Valeriy Kotenok on 23-Jun-17.
 */
public interface WalletInfoDao {
    List<WalletInfo> getAllWallets();

    WalletInfo getByName(String walletName);

    WalletInfo create(String walletName, String address);

    WalletInfo getById(Long id);
}
