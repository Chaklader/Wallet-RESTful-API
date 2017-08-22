package mobi.puut.database;

import mobi.puut.entities.WalletInfo;

import java.util.List;

public interface IWalletInfoDao {

    List<WalletInfo> getAllWallets();

    WalletInfo getByName(String walletName);

    WalletInfo getById(final Long id);

    WalletInfo create(String name, String currency, String address);

    void deleteWalletInfoByWalletId(Long walletId);

    WalletInfo getWalletInfoWithWalletNameAndCurrency(String walletName, String currencyName);
}
