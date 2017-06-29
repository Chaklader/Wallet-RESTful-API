//package mobi.puut.controllers;
//
//import org.bitcoinj.core.NetworkParameters;
//import org.bitcoinj.kits.WalletAppKit;
//import org.bitcoinj.params.MainNetParams;
//import org.bitcoinj.params.RegTestParams;
//import org.bitcoinj.params.TestNet3Params;
//import org.bitcoinj.wallet.DeterministicSeed;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.annotation.Nullable;
//import java.io.File;
//import java.io.IOException;
//
//
///**
// * Created by Chaklader on 6/12/17.
// */
//public class WalletMainController {
//
//    public static WalletAppKit bitcoin;
//
//     // public static NetworkParameters networkParameters = MainNetParams.get();
//    public static NetworkParameters networkParameters = TestNet3Params.get();
//
//    public static final String APP_NAME = "WalletTemplate";
//    public static final String WALLET_FILE_NAME = APP_NAME.replaceAll("[^a-zA-Z0-9.-]", "_")
//            + networkParameters.getPaymentProtocolId();
//
//    public static int NUMBER_OF_TRANSACTIONS = 100;
//    public static boolean includedDead = false;
//
//    private static final Logger logger = LoggerFactory.getLogger(WalletMainController.class);
//
//    private static WalletMainController controller;
//    private static WalletUIModel model = new WalletUIModel();
//
//    public WalletMainController() {
//
//        logger.info("Setup Wallet");
//        setupWalletKit(null);
//
//        try {
//            if (bitcoin.isChainFileLocked()) {
//                return;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        bitcoin.startAsync();
//    }
//
//    private void setupWalletKit(@Nullable DeterministicSeed seed) {
//
//        // if the seed is not null, that means we are restoring from the backup
//        bitcoin = new WalletAppKit(networkParameters, new File("."), WALLET_FILE_NAME) {
//
//            @Override
//            protected void onSetupCompleted() {
//
//                // Don't make the user wait for confirmations
//                // they're sending their own money anyway!!
//                bitcoin.wallet().allowSpendingUnconfirmedTransactions();
//                model.setWallet(bitcoin.wallet());
//            }
//        };
//
//        // Now configure and start the appkit. This will take a second or two - we could show a temporary splash screen
//        // or progress widget to keep the user engaged whilst we initialise, but we don't.
//        if (networkParameters == RegTestParams.get()) {
//            bitcoin.connectToLocalHost();   // You should run a regtest mode bitcoind locally.
//        } else if (networkParameters == TestNet3Params.get()) {
//            bitcoin.useTor();
//        }
//
//        bitcoin.setDownloadListener(model.getSyncProgressUpdater())
//                .setBlockingStartup(false)
//                .setUserAgent(APP_NAME, "1.0");
//
//        if (seed != null) {
//            bitcoin.restoreWalletFromSeed(seed);
//        }
//    }
//
//    public static WalletMainController getController() {
//        if (controller == null) {
//            controller = new WalletMainController();
//        }
//        return controller;
//    }
//
//    public static WalletUIModel getModel() {
//        if (model == null) {
//            model = new WalletUIModel();
//        }
//        return model;
//    }
//}
