package mobi.puut.controllers;


import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import org.bitcoinj.core.*;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.Wallet;
import org.spongycastle.crypto.params.KeyParameter;

import javax.annotation.Nullable;


/**
 * Created by Chaklader on 6/12/17.
 */
public class WalletSendMoneyController {

    private Wallet.SendResult sendResult;

    private KeyParameter aesKey;

    public static WalletSendMoneyController getSendMoneyController() {
        return new WalletSendMoneyController();
    }

    protected Coin parseCoin(final String amountOfCoin) {

        try {
            Coin amount = Coin.parseCoin(amountOfCoin);
            if (amount.getValue() <= 0) {
                throw new IllegalArgumentException("Invalid amount: " + amountOfCoin);
            }
            return amount;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid amount: " + amountOfCoin);
        }
    }

    public void send(final String address, final String amountStr) {

        try {
            Coin amount = Coin.parseCoin(amountStr);

            // cant send the money which is greater than the wallet
            if (amount.isGreaterThan(WalletManager.bitcoin.wallet().getBalance())) {
                return;
            }

            Address destination = Address.fromBase58(WalletManager.networkParameters, address);

            SendRequest sendRequest = null;

            // empty the wallet if we send the amount equal to the current balance
            if (amount.equals(WalletManager.bitcoin.wallet().getBalance())) {
                sendRequest = SendRequest.emptyWallet(destination);
            }

            // send as per intention
            else if (amount.isLessThan(WalletManager.bitcoin.wallet().getBalance())) {
                sendRequest = SendRequest.to(destination, amount);
            } else {

            }

            sendRequest.aesKey = aesKey;
            sendResult = WalletManager.bitcoin.wallet().sendCoins(sendRequest);

            Futures.addCallback(sendResult.broadcastComplete, new FutureCallback<Transaction>() {

                /**
                 *
                 * @param transaction take the wallet outbound trasaction (sending) as input
                 */
                @Override
                public void onSuccess(@Nullable Transaction transaction) {
                    String message = transaction.toString();
                }

                @Override
                public void onFailure(Throwable throwable) {
                    String error = "Could send money " + throwable.getMessage();
                }
            });

            sendRequest.tx.getConfidence().addEventListener((tx, reason) -> {
                if (reason == TransactionConfidence.Listener.ChangeReason.SEEN_PEERS) {
                    // TO DO
                }
            });
        } catch (InsufficientMoneyException e) {
            String error = "Could not empty the wallet. " +
                    "You may have too little money left in the wallet to make a transaction.";
        } catch (ECKey.KeyIsEncryptedException e) {
            String error = "Could not send money. " + "Remove the wallet password protection.";
        } catch (AddressFormatException e) {
            String error = "Could not send money. Invalid address: " + e.getMessage();
        } catch (Exception e) {
            String error = "Could not send money. " + e.getMessage();
        }
    }
}
