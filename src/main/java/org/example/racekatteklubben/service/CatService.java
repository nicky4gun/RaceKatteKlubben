package org.example.racekatteklubben.service;

import org.example.racekatteklubben.models.interfaces.ICatRepository;
import org.example.racekatteklubben.models.Cat;
import org.example.racekatteklubben.models.enums.Gender;
import org.example.racekatteklubben.models.enums.Race;
import org.example.racekatteklubben.models.enums.YearOrMonth;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatService {
    private final ICatRepository catRepository;

    public CatService(ICatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public int addCat(String imageUrl, String catName, Race race, int age, YearOrMonth yearOrMonth, Gender gender, int memberId) {
        validateCat(catName, race, age,yearOrMonth, gender);
        Cat cat = new Cat(imageUrl, catName, race, age,yearOrMonth, gender, memberId);
        return catRepository.createCat(cat);
    }

    public List<Cat> findCatsByMemberId(int memberId) {
        if (memberId <= 0) {
            throw new IllegalArgumentException("Invalid memberId");
        }

        return catRepository.findAllCatsByMemberId(memberId);
    }

    public Cat findCatById(int catId) {
        if (catId <= 0) {
            throw new IllegalArgumentException("Invalid catId");
        }

        return catRepository.findCatById(catId).orElseThrow(() ->
                new IllegalArgumentException("Kat med id " + catId + " blev ikke fundet"));
    }

    public void updateCat(int id, String imageUrl, String catName, Race race, int age, YearOrMonth yearOrMonth, Gender gender, int memberId) {
        Cat existingCat = findCatById(id);

        if (existingCat == null) {
            throw new IllegalArgumentException("Cat with id " + id + " does not exist");
        }

        validateCat(catName, race, age, yearOrMonth, gender);

        Cat cat = new Cat(id, imageUrl, catName, race, age,yearOrMonth, gender, memberId);
        catRepository.updateCat(cat);
    }

    public List<Cat> seachForCat(String keyword) {
      return catRepository.searchForCat( keyword);
    }

    public void removeCat(int catId) {catRepository.deleteCat(catId);}

    public void removeCatByMemberId(int memberId) {catRepository.deleteCatByMemberId(memberId);}


    private void validateCat(String catName, Race race, int age, YearOrMonth yearOrMonth, Gender gender) {
        if (catName == null || catName.isEmpty()) {
            throw new IllegalArgumentException("Du skal give en kat et navn");
        }

        if (race == null) {
            throw new IllegalArgumentException("Du skal vælge en race");
        }

        if ( age <= 0 || age > 18 ) {
            throw new IllegalArgumentException("Du skal angive en alder mellem 0 og 18");
        }

        if (yearOrMonth == null) {
            throw new IllegalArgumentException("Du skal angive et år eller en måned");
        }

        if (gender == null) {
            throw new IllegalArgumentException("Du skal angive et køn");
        }
    }
}
