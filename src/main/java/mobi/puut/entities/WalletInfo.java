package mobi.puut.entities;

import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Chaklader on 6/24/17.
 */
@Entity
@Table(name = "wallet_info")
public class WalletInfo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "currency")
    private String currency;

    public Long getId() {
        return id;
    }

    public WalletInfo(@NotNull String name, @NotNull String address, @NotNull String currency) {
        this.name = name;
        this.address = address;
        this.currency = currency;
    }

    public WalletInfo(@NotNull String name, @NotNull String address) {
        this.name = name;
        this.address = address;
    }

    public WalletInfo() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
