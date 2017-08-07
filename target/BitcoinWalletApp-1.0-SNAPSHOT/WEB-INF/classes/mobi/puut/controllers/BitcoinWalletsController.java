package mobi.puut.controllers;


import mobi.puut.entities.WalletInfo;
import mobi.puut.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * Created by Chaklader on 6/24/17.
 */
@Controller
public class BitcoinWalletsController {

    @GetMapping(value = "/")
    public String showHome(){
        return "home";
    }
}
