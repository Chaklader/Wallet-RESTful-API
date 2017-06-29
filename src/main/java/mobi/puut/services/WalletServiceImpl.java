package mobi.puut.services;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import mobi.puut.controllers.WalletManager;
import mobi.puut.controllers.WalletModel;
import mobi.puut.database.StatusDao;
import mobi.puut.database.UserDao;
import mobi.puut.database.WalletInfoDao;
import mobi.puut.entities.Status;
import mobi.puut.entities.User;
import mobi.puut.entities.WalletInfo;
import org.bitcoinj.core.*;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.Wallet;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Chaklader on 6/24/17.
 */
@Service
public class WalletServiceImpl implements WalletService {

    private static NetworkParameters params = MainNetParams.get();

    private Map<String, WalletManager> genWalletMap = new ConcurrentHashMap<>();

    private Map<Long, WalletManager> walletMangersMap = new ConcurrentHashMap<>();

    @Autowired
    private WalletInfoDao walletInfoDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private StatusDao statusDao;

    @Override
    public List<WalletInfo> getAllWallets() {

        try {
            return walletInfoDao.getAllWallets();
        } catch (HibernateException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public synchronized void generateAddress(final String walletName) {
        WalletInfo walletInfo = walletInfoDao.getByName(walletName);
        if (walletInfo == null) {
            if (genWalletMap.get(walletName) == null) {
                final WalletManager walletManager = WalletManager.setupWallet(walletName);
                walletManager.addWalletSetupCompletedListener((wallet) -> {
                    Address address = wallet.currentReceiveAddress();
                    WalletInfo newWallet = createWalletInfo(walletName, address.toString());

                    walletMangersMap.put(newWallet.getId(), walletManager);
                    genWalletMap.remove(walletName);
                });
                genWalletMap.put(walletName, walletManager);
            }
        }
    }

    @Override
    public WalletModel getWalletModel(final Long id) {
        WalletManager walletManager = getWalletManager(id);
        WalletModel model = walletManager == null ? null : walletManager.getModel();
        return model;
    }

    @Override
    public WalletModel sendMoney(final Long walletId, final String amount, final String address) {

        User user = getCurrentUser();
        WalletModel model = null;
        WalletManager walletManager = getWalletManager(walletId);
        if (walletManager != null) {
            Wallet wallet = walletManager.getBitcoin().wallet();
            send(user, walletId, wallet, address, amount);
            model = walletManager.getModel();
        }
        return model;
    }

    @Override
    public List<Status> getWalletStatuses(final Long id) {
        return statusDao.getByWalletId(id);
    }

    @Override
    public WalletInfo getWalletInfo(Long walletId) {
        return walletInfoDao.getById(walletId);
    }

    protected void send(final User user, final Long walletId, final Wallet wallet,
                        final String address, final String amountStr) {
        // Address exception cannot happen as we validated it beforehand.
        Coin balance = wallet.getBalance();
        try {
            Coin amount = parseCoin(amountStr);
            Address destination = Address.fromBase58(params, address);

            SendRequest req;
            if (amount.equals(balance))
                req = SendRequest.emptyWallet(destination);
            else
                req = SendRequest.to(destination, amount);

            Wallet.SendResult sendResult = wallet.sendCoins(req);

            Futures.addCallback(sendResult.broadcastComplete, new FutureCallback<Transaction>() {
                @Override
                public void onSuccess(@Nullable Transaction result) {
                    String message = result.toString();
                    saveTransaction(user, walletId, address, message, balance);
                }

                @Override
                public void onFailure(Throwable t) {
                    String error = "Could send money. " + t.getMessage();
                    saveTransaction(user, walletId, address, error, balance);
                }
            });
            sendResult.tx.getConfidence().addEventListener((tx, reason) -> {
                if (reason == TransactionConfidence.Listener.ChangeReason.SEEN_PEERS) {
                    //todo
                }
            });
        } catch (InsufficientMoneyException e) {
            String error = "Could not empty the wallet. " +
                    "You may have too little money left in the wallet to make a transaction.";
            saveTransaction(user, walletId, address, error, balance);

        } catch (ECKey.KeyIsEncryptedException e) {
            String error = "Could send money. " + "Remove the wallet password protection.";
            saveTransaction(user, walletId, address, error, balance);
        } catch (AddressFormatException e) {
            String error = "Could send money. Invalid address: " + e.getMessage();
            saveTransaction(user, walletId, address, error, balance);
        } catch (Exception e) {
            String error = "Could send money. " + e.getMessage();
            saveTransaction(user, walletId, address, error, balance);
        }
    }

    protected Coin parseCoin(final String amountStr) {
        try {
            Coin amount = Coin.parseCoin(amountStr);
            if (amount.getValue() <= 0) {
                throw new IllegalArgumentException("Invalid amount: " + amountStr);
            }
            return amount;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid amount: " + amountStr);
        }
    }

    protected Status saveTransaction(final User user, final Long walletId, final String address,
                                     final String message, final Coin balance) {
        Status status = new Status();
        status.setAddress(address.length() > 90 ? address.substring(0, 89) : address);
        status.setUser_id(user.getId());
        status.setWallet_id(walletId);
        status.setTransaction(message.length() > 90 ? message.substring(0, 89) : message);
        status.setBalance(balance.getValue());
        return statusDao.saveStatus(status);
    }

    protected User getCurrentUser() {
        User user = userDao.getById(1); //TODO
        return user;
    }

    protected synchronized WalletManager getWalletManager(final Long id) {
        WalletManager walletManager = walletMangersMap.get(id);
        if (walletManager == null) {
            WalletInfo walletInfo = walletInfoDao.getById(id);
            if (walletInfo != null) {
                String name = walletInfo.getName();
                walletManager = WalletManager.setupWallet(name);
                walletMangersMap.put(walletInfo.getId(), walletManager);
            }
        }
        return walletManager;
    }

    protected WalletInfo createWalletInfo(final String walletName, final String address) {
        return walletInfoDao.create(walletName, address);
    }
}
