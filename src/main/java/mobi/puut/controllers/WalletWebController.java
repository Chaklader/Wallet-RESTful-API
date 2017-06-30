package mobi.puut.controllers;

import mobi.puut.entities.WalletInfo;
import mobi.puut.services.WalletService;
import org.bitcoinj.core.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Chaklader on 6/24/17.
 */
@RestController
@RequestMapping("/rest")
public class WalletWebController {

    @Autowired
    private WalletService walletService;

    /**
     * @return return the number of wallet created as String
     */
    @ResponseBody
    @RequestMapping("/walletsNumber")
    public String getWalletsCount() {
        List<WalletInfo> wallets = walletService.getAllWallets();
        return String.valueOf(wallets.size());
    }


    /**
     * @param id takes wallet index as the Long ID argument
     * @return return the balance of the request wallet
     */
    @ResponseBody
    @RequestMapping("/walletBalance")
    public String getWalletBalance(@RequestParam final Long id) {
        WalletModel walletModel = walletService.getWalletModel(id);
        return String.valueOf(walletModel.getBalanceFloatFormat());
    }

    /**
     * @param id takes wallet index as the Long ID argument
     * @return return the number of transaction executed on
     * the requested wallet
     */
    @ResponseBody
    @RequestMapping("/walletTransactionsNumber")
    public String getWalletTransactionsNumber(@RequestParam final Long id) {

        WalletModel walletModel = walletService.getWalletModel(id);
        List<Transaction> history = walletModel.getTransactions();
        return String.valueOf(history.size());
    }
}
