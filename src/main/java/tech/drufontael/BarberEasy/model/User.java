package tech.drufontael.BarberEasy.model;

import jakarta.persistence.*;
import tech.drufontael.BarberEasy.util.Util;

import java.util.Objects;
import java.util.UUID;

@Entity(name = "tb_user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type",discriminatorType = DiscriminatorType.STRING)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String email;
    private String password;

    public User() {
    }

    public User(UUID id, String name, String email, String password) {
        this.id = id;
        this.username = name;
        if(Util.emailValidator(email)) {
            this.email = email;
        }else {
            throw new IllegalArgumentException("Invalid email");
        }
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
