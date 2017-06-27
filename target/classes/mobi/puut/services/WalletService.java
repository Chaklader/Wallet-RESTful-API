package mobi.puut.services;

import mobi.puut.controllers.WalletModel;
import mobi.puut.entities.Status;
import mobi.puut.entities.WalletInfo;

import java.util.List;

/**
 * Created by Chaklader on 6/23/17.
 */
public interface WalletService {

    List<WalletInfo> getAllWallets();

    void generateAddress(String walletName);

    WalletModel getWalletModel(Long id);

    WalletModel sendMoney(Long walletId, String amount, String address);

    List<Status> getWalletStatuses(Long id);

    WalletInfo getWalletInfo(Long id);
}
