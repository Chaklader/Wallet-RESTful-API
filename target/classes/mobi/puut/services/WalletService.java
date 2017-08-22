package mobi.puut.services;

import mobi.puut.controllers.WalletModel;
import mobi.puut.entities.Status;
import mobi.puut.entities.User;
import mobi.puut.entities.WalletInfo;
import org.bitcoinj.core.Coin;
import org.springframework.stereotype.Service;

import java.util.List;


public interface WalletService {

    List<Status> getWalletStatuses(final Long id);

    WalletInfo getWalletInfo(Long walletId);

    List<WalletInfo> getAllWallets();

    WalletInfo generateAddress(final String walletName, String currencyName);

    WalletInfo getWalletInfoWithCurrencyAndWalletName(String walletName, String currencyName);

    WalletModel getWalletModel(final Long walletId);

    WalletModel sendMoney(final Long walletId, final String amount, final String address);

    void deleteWalletInfoById(Long id);
}
