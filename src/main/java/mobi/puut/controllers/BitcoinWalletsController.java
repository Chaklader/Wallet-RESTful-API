package mobi.puut.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


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
