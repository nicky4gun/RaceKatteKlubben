package org.example.racekatteklubben.infrastructure;

import org.example.racekatteklubben.models.Cat;

import java.util.List;
import java.util.Optional;

public interface ICatRepository {
    int createCat(Cat cat);

    List<Cat> findAllCatsByMemberId(int memberId);

    Optional<Cat> findCatById(int id);

    void updateCat(Cat cat);

    void deleteCat(int catId);
}
