package org.example.racekatteklubben.models;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private int id;
    private String memberName;
    private String email;
    private String password;
    List<Cat> cats = new ArrayList<>();
    private boolean admin;

    public Member() {}

    public Member(String memberName, String email, String password, boolean admin) {
        this.memberName = memberName;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }

    public Member(int id, String memberName, String email, String password, boolean admin) {
        this.id = id;
        this.memberName = memberName;
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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
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

    public List<Cat> getCats() {
        return cats;
    }

    public void addCat(Cat cat) {
        cats.add(cat);
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
