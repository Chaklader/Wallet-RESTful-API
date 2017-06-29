//package mobi.puut.controllers;
//
//import mobi.puut.entities.Send;
//import mobi.puut.entities.Status;
//import mobi.puut.services.BitcoinWalletService;
//import org.bitcoinj.core.Coin;
//import org.bitcoinj.core.Transaction;
//import org.bitcoinj.wallet.Wallet;
//import org.bitcoinj.wallet.listeners.AbstractWalletEventListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.List;
//
//import static mobi.puut.controllers.WalletMainController.bitcoin;
//
///**
// * Created by Chaklader on 6/12/17.
// */
////@Controller
//public class BitcoinWalletController {
//
//    @Autowired
//    BitcoinWalletService bitcoinWalletService;
//
//  // WalletUIModel model = new WalletUIModel();
//
//    /**
//     * @return return the landing page of the app with account balance, address,
//     * transaction history and option for performing the sending operation.
//     */
//    @RequestMapping(value = "/")
//    public String showBitcoinWallet() {
//
//        Status status = new Status();
//
//        // add the status attributes here
//      /*  status.setUser_id(model.getUserId());
//        status.setAddress(model.getAddress().toString());
//        status.setAddress(model.getTransaction());
//        status.setAddress(model.getBalance().toString());*/
//
//        return "index";
//    }
//
//    /**
//     * @param amount  amount in the BTC to send
//     * @param address address to send the BTC
//     * @return after performing the sending operation in
//     * the pop-up box, it returns to the original landing page
//     */
//    @RequestMapping(value = "/sendMoney", method = RequestMethod.POST)
//    public ModelAndView PostBitcoinWallet(@RequestParam String amount, @RequestParam String address) {
//
//        ModelMap modelMap = new ModelMap();
//        Send send = new Send();
//        send.setAmount(amount);
//        send.setAddress(address);
//        modelMap.put("send", send);
//        return new ModelAndView("sendMoney", modelMap);
//    }
//
//    @RequestMapping("/statuses")
//    public String showStatuses(Model model) {
//
////        bitcoinWalletService.throwTestException();
//
//        List<Status> statuses = bitcoinWalletService.getCurrentStatuses();
//        model.addAttribute("statuses", statuses);
//        return "statuses";
//    }
//
//    /**
//     * @return the method return a boolean confirms the money
//     * is received in the blockchain for the respective address
//     */
//    public static boolean isMoneyReceived() {
//
//        try {
//
//            bitcoin.wallet().addEventListener(new AbstractWalletEventListener() {
//
//                @Override
//                public void onCoinsReceived(Wallet w, Transaction tx, Coin prevBalance, Coin newBalance) {
//                    // Runs in the dedicated "user thread".
//                    //
//                    // The transaction "tx" can either be pending, or included into a block (we didn't see the broadcast).
//                    Coin value = tx.getValueSentToMe(w);
//
////                    System.out.println("Received tx for " + value.toFriendlyString() + ": " + tx);
////                    System.out.println("Transaction will be forwarded after it confirms.");
//                }
//            });
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            return false;
//        }
//    }
//}
