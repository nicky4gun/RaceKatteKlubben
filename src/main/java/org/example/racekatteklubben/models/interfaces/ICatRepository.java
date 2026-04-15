package org.example.racekatteklubben.models.interfaces;

import org.example.racekatteklubben.models.Cat;

import java.util.List;
import java.util.Optional;

public interface ICatRepository {

    int createCat(Cat cat);

    List<Cat> findAllCatsByMemberId(int memberId);

    Optional<Cat> findCatById(int id);

    void updateCat(Cat cat);

    List<Cat> searchForCat(String name, int memberId);

    void deleteCat(int catId);

    void deleteCatByMemberId(int memberId);
}
