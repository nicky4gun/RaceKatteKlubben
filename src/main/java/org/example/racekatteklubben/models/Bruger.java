package org.example.racekatteklubben.models;

public class Bruger {
    private int id;
    private String email;
    private String password;
    private boolean admin;

    public Bruger(String email, String password, boolean admin ){

    }

    public Bruger(int id,String email, String password, boolean admin) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
