package org.example.racekatteklubben.models;

import org.example.racekatteklubben.models.enums.Race;

import java.util.List;

public class Cat {
    private int id;
    private String images;
    private String name;
    private Race race;
     private int age;
     private String gender;
     private List<String> awards;
     private String owner;

     public Cat(String images, String name, Race race, int age, String gender, List<String> awards,String owner ) {

     }
     public Cat(int id, String images, String name, Race race, int age, String gender, List<String> awards,String owner ) {
         this.id = id;
         this.images = images;
         this.name = name;
         this.race = race;
         this.age = age;
         this.gender = gender;
         this.awards = awards;
         this.owner = owner;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public String getOwner() {
         return owner;
    }
    public void setOwner(String owner) {
         this.owner = owner;
    }
}
