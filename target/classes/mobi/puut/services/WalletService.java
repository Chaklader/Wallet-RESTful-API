package mobi.puut.services;

import mobi.puut.controllers.WalletModel;
import mobi.puut.entities.Status;
import mobi.puut.entities.WalletInfo;

import java.util.List;

/**
 * Created by Chaklader on 6/24/17.
 */
public interface WalletService {

    List<WalletInfo> getAllWallets();

    WalletInfo generateAddress(String walletName, String currencyName);

    WalletModel getWalletModel(Long id);

    WalletModel sendMoney(Long walletId, String amount, String address);

    List<Status> getWalletStatuses(Long id);

    WalletInfo getWalletInfo(Long id);

    WalletInfo getWalletInfoWithCurrencyAndWalletName(String currencyName, String walletName);

    void deleteWalletInfoById(Long id);
}
