package tech.drufontael.BarberEasy.dto;

import tech.drufontael.BarberEasy.model.Barber;


public class BarberDTO {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String barberInfo;

    public BarberDTO(Long id, String name, String email, String password, String barberInfo) {
        this.id = id;
        this.username = name;
        this.email = email;
        this.password = password;
        this.barberInfo = barberInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getBarberInfo() {
        return barberInfo;
    }

    public void setBarberInfo(String barberInfo) {
        this.barberInfo = barberInfo;
    }

    public Barber toBarber() {
        return new Barber(id, username, email, password, barberInfo);
    }
}

