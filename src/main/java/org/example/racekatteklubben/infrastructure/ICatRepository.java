package org.example.racekatteklubben.infrastructure;

import org.example.racekatteklubben.models.Cat;

public interface ICatRepository {
    int createCat(Cat cat);
}
