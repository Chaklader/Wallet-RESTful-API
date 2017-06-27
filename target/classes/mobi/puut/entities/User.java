package mobi.puut.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Chaklader on 6/13/17.
 */
@Entity
@Table(name = "users")
public class User {

    @NotNull
    private int id;

    @Size(min = 5, max = 45, message = "Name must be between 5 and 45 characters.")
    private String name;

    public User() {

    }

    public User(@Size(min = 5, max = 45, message = "Name must be between 5 and 45 characters.") String name) {

        this.name = name;
    }

    public User(int id, String name) {

        super();
        this.id = id;
        this.name = name;
    }

    @Id
    @Column
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getId() != user.getId()) return false;
        return getName().equals(user.getName());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getName().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
