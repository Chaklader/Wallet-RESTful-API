package mobi.puut.controllers;

import mobi.puut.controllers.WalletModel;
import mobi.puut.entities.WalletInfo;
import mobi.puut.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Valeriy Kotenok on 24-Jun-17.
 */
@RestController
@RequestMapping("/rest")
public class WalletWebService {

    @Autowired
    private WalletService walletService;

    @RequestMapping("/walletsNumber")
    @ResponseBody
    public String getWalletsCount() {
        List<WalletInfo> wallets = walletService.getAllWallets();
        return String.valueOf(wallets.size());
    }

    @RequestMapping("/walletBalance")
    @ResponseBody
    public String getWalletBalance(@RequestParam final Long id) {
        WalletModel walletModel = walletService.getWalletModel(id);
        return String.valueOf(walletModel.getBalanceFloatFormat());
    }

    @RequestMapping("/walletTransactionsNumber")
    @ResponseBody
    public String getWalletTransactionsNumber(@RequestParam final Long id) {
        WalletModel walletModel = walletService.getWalletModel(id);
        List<String> history = walletModel.getHistory();
        return String.valueOf(history.size());
    }

}
