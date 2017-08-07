package mobi.puut.controllers;

import mobi.puut.entities.WalletInfo;
import mobi.puut.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



/**
 * Created by Chaklader on 6/24/17.
 */
@Controller
public class BitcoinWalletsController {

    @RequestMapping(value = "/")
    public String showHome(){
        return "home";
    }
}
