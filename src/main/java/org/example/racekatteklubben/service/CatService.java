package org.example.racekatteklubben.service;

import org.example.racekatteklubben.infrastructure.CatRepository;
import org.example.racekatteklubben.infrastructure.ICatRepository;
import org.example.racekatteklubben.infrastructure.IMemberRepository;
import org.example.racekatteklubben.models.Cat;
import org.example.racekatteklubben.models.enums.Race;
import org.springframework.stereotype.Service;

@Service
public class CatService {
    private final ICatRepository catRepository;
    private final IMemberRepository memberRepository;

    public CatService(ICatRepository catRepository, IMemberRepository memberRepository) {
        this.catRepository = catRepository;
        this.memberRepository = memberRepository;
    }

    public void addCat(String imageUrl, String catName, Race race, int age, String gender, int memberId) {
        validateCat(imageUrl, catName, race, age, gender, memberId);
        Cat cat = new Cat(imageUrl, catName, race, age, gender, memberId);
        catRepository.createCat(cat);
    }
    public void removeCat(int catid) {catRepository.deleteCat(catid);}

    private void validateCat(String imageUrl, String catName, Race race, int age, String gender, int memberId) {}
}
