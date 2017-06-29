package mobi.puut.services;

import mobi.puut.database.BitcoinWalletDAO;
import mobi.puut.entities.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Chaklader on 6/13/17.
 */

//@Service("bitcoinWalletService")
public class BitcoinWalletService {

    @Autowired
    public BitcoinWalletDAO bitcoinWalletDAO;

    public List<Status> getCurrentStatuses() {
        return bitcoinWalletDAO.getAllWalletStatus();
    }

    public void create(Status offer) {
        bitcoinWalletDAO.saveOrUpdate(offer);
    }
}

