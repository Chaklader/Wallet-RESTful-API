package mobi.puut.controllers;

import org.bitcoinj.wallet.Wallet;

/**
 * Created by Valeriy Kotenok on 23-Jun-17.
 */
public interface WalletSetupCompletedListener {
    void onSetupCompleted(Wallet wallet);
}
