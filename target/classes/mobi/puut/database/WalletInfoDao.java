package mobi.puut.database;

import mobi.puut.entities.WalletInfo;

import java.util.List;

/**
 * Created by Chaklader on 6/24/17.
 */
public interface WalletInfoDao {

    List<WalletInfo> getAllWallets();

    WalletInfo getByName(String walletName);

    WalletInfo create(String walletName, String address, String currencyName);

    WalletInfo getById(Long id);

    void deleteWalletInfoById(Long id);

    WalletInfo getWalletInfoWithCurrencyAndWalletName(String currencyName, String walletName);
}
