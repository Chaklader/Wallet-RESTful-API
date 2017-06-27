package mobi.puut.entities;

import javax.persistence.*;

/**
 * Created by Chaklader on 6/23/17.
 */
@Entity
@Table(name = "wallet_info")
public class WalletInfo {

    private Long id;

    private String name;

    private String address;

    @Id
    @Column
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
