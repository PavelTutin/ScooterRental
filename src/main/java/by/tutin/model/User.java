package by.tutin.model;

import by.tutin.model.enums.Role;
import by.tutin.model.enums.UserStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "user")
public class User extends AEntity {

    private String username;
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private int subscription;
    private int discount;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status=" + status +
                ", subscription=" + subscription +
                ", discount=" + discount +
                ", role=" + role +
                '}';
    }
}
