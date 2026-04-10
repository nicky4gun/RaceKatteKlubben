package org.example.racekatteklubben.models;

import org.example.racekatteklubben.models.enums.Race;

import java.util.List;

public class Cat {
    private int id;
    private String images;
    private String catName;
    private Race race;
    private int age;
    private String gender;
    private List<String> awards;
    private int ownerId;

    public Cat(String images, String catName, Race race, int age, String gender, int ownerId) {
        this.images = images;
        this.catName = catName;
        this.race = race;
        this.age = age;
        this.gender = gender;
        this.ownerId = ownerId;
    }

    public Cat(int id, String images, String catName, Race race, int age, String gender, int ownerId) {
        this.id = id;
        this.images = images;
        this.catName = catName;
        this.race = race;
        this.age = age;
        this.gender = gender;
        this.ownerId = ownerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getAwards() {
        return awards;
    }

    public void setAwards(List<String> awards) {
        this.awards = awards;
    }

    public int getOwnerId() {
         return ownerId;
    }

    public void setOwnerId(String owner) {
         this.ownerId = ownerId;
    }
}
