package mobi.puut.entities;

/**
 * Created by Chaklader on 6/15/17.
 */
public class Send {

    private int id;

    private String amount;

    private String address;

    public Send() {}

    public Send(int id, String amount, String address) {
        this.id = id;
        this.amount = amount;
        this.address = address;
    }

    public Send(String amount, String address) {
        super();
        this.amount = amount;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Send)) return false;

        Send send = (Send) o;

        if (getId() != send.getId()) return false;
        if (!getAmount().equals(send.getAmount())) return false;
        return getAddress().equals(send.getAddress());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getAmount().hashCode();
        result = 31 * result + getAddress().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Send{" +
                "id=" + id +
                ", amount='" + amount + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
