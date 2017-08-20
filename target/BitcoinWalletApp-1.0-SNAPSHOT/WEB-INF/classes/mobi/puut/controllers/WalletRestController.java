package mobi.puut.controllers;

import mobi.puut.entities.User;
import mobi.puut.entities.WalletInfo;
import mobi.puut.entities.WalletWithMoneyRequest;
import mobi.puut.services.UserService;
import mobi.puut.services.WalletService;
import org.bitcoinj.core.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Chaklader on 6/24/17.
 */
@RestController
@RequestMapping("/rest")
public class WalletRestController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;


    /**
     * get all the WalletInfo entities
     * curl -G http://localhost:8080/rest/wallets | json
     *
     * @return
     */
    @RequestMapping(value = "/wallets", method = RequestMethod.GET)
    public ResponseEntity<List<WalletInfoWrapper>> getAllWalletInfo() {

        List<WalletInfo> walletInfos = walletService.getAllWallets();

        if (Objects.isNull(walletInfos)) {
            return new ResponseEntity<List<WalletInfoWrapper>>(HttpStatus.NO_CONTENT);
        }

        List<WalletInfoWrapper> walletInfoWrappers = new ArrayList<>();

        // hiding the entity ids for the security purposes
        walletInfos.forEach(w -> walletInfoWrappers.add(new WalletInfoWrapper(w.getName(), w.getAddress())));

        return new ResponseEntity<List<WalletInfoWrapper>>(walletInfoWrappers, HttpStatus.OK);
    }


    /**
     * get all the users
     * curl -G http://localhost:8080/rest/users | json
     *
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserWrapper>> getAllUsers() {

        List<User> users = userService.getAllUsers();

        if (Objects.isNull(users)) {
            return new ResponseEntity<List<UserWrapper>>(HttpStatus.NO_CONTENT);
        }

        List<UserWrapper> userWrappers = new ArrayList<>();

        // hiding the user Ids for security purpose
        users.forEach(user -> userWrappers.add(new UserWrapper(user.getName())));

        return new ResponseEntity<List<UserWrapper>>(userWrappers, HttpStatus.OK);
    }


    /**
     * get the info of the specific wallet
     * curl -i -H "Accept: application/json" http://localhost:8080/rest/wallets/1 | json
     * curl -G "Accept: application/json" http://localhost:8080/rest/wallets/1 | json
     * curl -X GET "Accept: application/json" http://localhost:8080/rest/wallets/1 | json
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/wallets/{id}", method = RequestMethod.GET)
    public ResponseEntity<WalletInfoWrapper> getWalletById(@PathVariable("id") long id) {

        logger.info("Get the wallet with Id =  {}", id);

        WalletInfo walletInfo = getWalletInfo(id);

        if (walletInfo == null) {
            return new ResponseEntity<WalletInfoWrapper>(HttpStatus.NOT_FOUND);
        }

        // set the wallet name, address and the currency in the walletInfo wrapper
        WalletInfoWrapper walletInfoWrapper = new WalletInfoWrapper(walletInfo.getName(),
                walletInfo.getAddress(), walletInfo.getCurrency());

        return new ResponseEntity<WalletInfoWrapper>(walletInfoWrapper, HttpStatus.OK);
    }


    // TODO
    // A. write a RESTful method using the wallet id to get info from the status table

    // TODO
    // B. write a RESTful method for the receiving operations


    /**
     * get the wallet address with the currency name and the wallet name
     * <p>
     * returns the Long value for the walletInfo
     * curl -i -H "Accept: text/html" http://localhost:8080/rest/wallets/bitcoin/puut | json
     * <p>
     * <p>
     * returns the String value for the walletInfo address
     * curl -i -H "Accept: text/html" http://localhost:8080/rest/wallets/bitcoin/puut/true | json
     *
     * @param currencyName
     * @param walletName
     * @return
     */
    @RequestMapping(value = "wallets/{currencyName}/{walletName}", method = RequestMethod.GET
            , produces = "text/html")
    public ResponseEntity<?> getAddressWithCurrencyAndWalletName(@PathVariable("currencyName") String currencyName,
                                                                 @PathVariable("walletName") String walletName
            , @RequestParam(value = "address", required = false) boolean address) {

        logger.info("The currency name is {} and wallet name is {}", currencyName, walletName);
        WalletInfo walletInfo = walletService.getWalletInfoWithCurrencyAndWalletName(walletName, currencyName);

        if (Objects.isNull(walletInfo)) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

        // The GET request contains a boolean true for the address
        // address values is expected and will be returned
        if (address) {
            String addressValue = walletInfo.getAddress();
            return new ResponseEntity<String>(addressValue, HttpStatus.OK);
        } else {
            Long walletId = walletInfo.getId();
            return new ResponseEntity<Long>(walletId, HttpStatus.OK);
        }
    }


    /**
     * generate the address from the provided wallet walletName
     * <p>
     * curl -H "Content-Type: application/json" -X POST -d '{"walletName":"Ice","currencyName":"Bitcoin"}' http://localhost:8080/rest/generateAddress
     *
     * @param walletWithMoneyRequest
     * @return
     */
    @RequestMapping(value = "/generateAddress", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletInfoWrapper> generateAddress(@RequestBody @Validated WalletWithMoneyRequest walletWithMoneyRequest) {

        String walletName = walletWithMoneyRequest.getWalletName();

        String currencyName = walletWithMoneyRequest.getCurrencyName();

        logger.info("walletName {} and currencyName {}", walletName, currencyName);

        // return if the wallet name or the currency is null
        if (Objects.isNull(walletName) || Objects.isNull(currencyName)) {
            return new ResponseEntity<WalletInfoWrapper>(HttpStatus.NOT_ACCEPTABLE);
        }

        WalletInfo walletInfo = walletService.generateAddress(walletName, currencyName);

        if (Objects.isNull(walletInfo)) {
            return new ResponseEntity<WalletInfoWrapper>(HttpStatus.NOT_ACCEPTABLE);
        }

        WalletInfoWrapper walletInfoWrapper = new WalletInfoWrapper();
        walletInfoWrapper.setName(walletInfo.getName());

        return new ResponseEntity<WalletInfoWrapper>(walletInfoWrapper, HttpStatus.CREATED);
    }


    /**
     * curl -X POST -d "amount=0.56&address=myuoXZrmSKtybRzkR5GfJm4LNtUoUorqsn" http://localhost:8080/rest/sendMoney/3
     * send money to the external users
     *
     * @param walletId
     * @param amount
     * @param address
     * @return
     */
    @RequestMapping(value = "/sendMoney/{walletId}", method = RequestMethod.POST)
    public ResponseEntity<WalletModelWrapper> sendMoneyByWalletId(@PathVariable("walletId") Long walletId,
                                                                  @RequestBody String amount, @RequestBody String address) {

        WalletModel walletModel = getWalletModel(walletId);

        if (Objects.isNull(walletModel)) {
            return new ResponseEntity<WalletModelWrapper>(HttpStatus.NOT_FOUND);
        }

        walletModel = walletService.sendMoney(walletId, amount, address);

        WalletModelWrapper walletModelWrapper = new WalletModelWrapper();
        walletModelWrapper.setAddress(walletModel.getAddress().toString());
        walletModelWrapper.setBalance(walletModel.getBalance().toString());

        return new ResponseEntity<WalletModelWrapper>(walletModelWrapper, HttpStatus.OK);
    }


    // curl -i -X DELETE http://localhost:8080/rest/delete/9

    /**
     * delete a wallet with the Id
     *
     * @param walletInfoId
     * @return
     */
    @RequestMapping(value = "/delete/{walletInfoId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletInfoWrapper> deleteWalletInfoById(@PathVariable("walletInfoId") long walletInfoId) {

        WalletInfo walletInfo = getWalletInfo(walletInfoId);

        if (walletInfo == null) {
            System.out.println("The WalletInfo obj with id = " + walletInfoId + " is not found");
            return new ResponseEntity<WalletInfoWrapper>(HttpStatus.NOT_FOUND);
        }

        walletService.deleteWalletInfoById(walletInfoId);

        WalletInfoWrapper walletInfoWrapper = new WalletInfoWrapper();
        walletInfoWrapper.setName(walletInfo.getName());
        walletInfoWrapper.setAddress(walletInfo.getAddress());

        return new ResponseEntity<WalletInfoWrapper>(HttpStatus.NO_CONTENT);
    }


    /**
     * get the wallet balance with the Id
     * curl -G http://localhost:8080/rest/balanace/1 | json
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/balanace/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getWalletBalanceById(@PathVariable("id") long id) {

        WalletModel walletModel = getWalletModel(id);

        if (Objects.isNull(walletModel)) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

        String balanace = String.valueOf(walletModel.getBalanceFloatFormat());
        return new ResponseEntity<String>(balanace, HttpStatus.OK);
    }


    /**
     * get the list of the transactions with the wallet Id
     * curl -G http://localhost:8080/rest/transactions/1 | json
     *
     * @param walletId
     * @return
     */
    @RequestMapping(value = "/transactions/{walletId}", method = RequestMethod.GET)
    public ResponseEntity<List<String>> readAllTransactionsByWalletId(@PathVariable("walletId") Long walletId) {

        WalletModel walletModel = getWalletModel(walletId);

        if (Objects.isNull(walletModel)) {
            return new ResponseEntity<List<String>>(HttpStatus.NOT_FOUND);
        }

        List<Transaction> transactions = walletModel.getTransactions();

        List<String> list = new ArrayList<>();

        for (Transaction transaction : transactions) {
            list.add(walletModel.addTransactionHistory(transaction));
        }

        return new ResponseEntity<List<String>>(list, HttpStatus.OK);
    }


    /**
     * http://localhost:8080/rest/walletsNumber
     *
     * @return return the number of wallet created as String
     */
    @ResponseBody
    @RequestMapping("/walletsNumber")
    public String getWalletsCount() {
        List<WalletInfo> wallets = walletService.getAllWallets();
        return String.valueOf(wallets.size());
    }


    /**
     * http://localhost:8080/rest/walletBalance
     *
     * @param id takes wallet index as the Long ID argument
     * @return return the balance of the request wallet
     */
    @ResponseBody
    @RequestMapping("/walletBalance")
    public String getWalletBalance(@RequestParam final Long id) {

        WalletModel walletModel = getWalletModel(id);
        return String.valueOf(walletModel.getBalanceFloatFormat());
    }


    /**
     * http://localhost:8080/rest/walletTransactionsNumber
     *
     * @param id takes wallet index as the Long ID argument
     * @return return the number of transaction executed on
     * the requested wallet
     */
    @ResponseBody
    @RequestMapping("/walletTransactionsNumber")
    public String getWalletTransactionsNumber(@RequestParam final Long id) {

        WalletModel walletModel = getWalletModel(id);
        List<Transaction> history = walletModel.getTransactions();
        return String.valueOf(history.size());
    }


    /**
     * get the WalletModel bt the Id
     *
     * @param id
     * @return
     */
    private WalletModel getWalletModel(Long id) {
        return walletService.getWalletModel(id);
    }

    /**
     * get the WalletInfo entity by id
     *
     * @param id
     * @return
     */
    private WalletInfo getWalletInfo(Long id) {
        return walletService.getWalletInfo(id);
    }


    /**
     * a wrapper class of the WalletInfo class
     */
    private class WalletInfoWrapper {

        String name;

        String address;

        // currency such as bitcoin, ethereum, litecoin, nem, ripple, dash etc
        String currencyName;

        public String getCurrencyName() {
            return currencyName;
        }

        public void setCurrencyName(String currencyName) {
            this.currencyName = currencyName;
        }

        public WalletInfoWrapper() {
        }

        public WalletInfoWrapper(String name, String address) {
            this.name = name;
            this.address = address;
        }

        public WalletInfoWrapper(String name, String address, String currencyName) {
            this.name = name;
            this.address = address;
            this.currencyName = currencyName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    /**
     * class to the wrap the User class for hiding the Ids
     */
    private class UserWrapper {

        String name;

        public UserWrapper(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    /**
     * a private claas for wrapping the WalletModel
     */
    private class WalletModelWrapper {

        String address;

        String balance;

        public WalletModelWrapper(String address, String balance) {
            this.address = address;
            this.balance = balance;
        }

        public WalletModelWrapper() {
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }
    }


    // Wrapper class for the status table
    private class StatusWrapper {

        String address;

        String balance;

        String transactions;

        public StatusWrapper(String address, String balance, String transactions) {

            this.address = address;
            this.balance = balance;
            this.transactions = transactions;
        }

        public StatusWrapper() {
        }


        public StatusWrapper(String address, String balance) {
            this.address = address;
            this.balance = balance;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getTransactions() {
            return transactions;
        }

        public void setTransactions(String transactions) {
            this.transactions = transactions;
        }
    }
}


