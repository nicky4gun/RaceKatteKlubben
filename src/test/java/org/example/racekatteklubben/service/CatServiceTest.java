package org.example.racekatteklubben.service;

import org.example.racekatteklubben.models.interfaces.ICatRepository;
import org.example.racekatteklubben.models.Cat;
import org.example.racekatteklubben.models.enums.Gender;
import org.example.racekatteklubben.models.enums.Race;
import org.example.racekatteklubben.models.enums.YearOrMonth;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatServiceTest {

    @Mock
    private ICatRepository catRepository;

    @InjectMocks
    private CatService catService;

    // addCat() - Tests
    @Test
    void addCat_ShouldAddCat_WhenValidInput() {
        String imageUrl = "IMG_1428.png";
        String catName = "Whiskers";
        Race race = Race.Birman;
        int age = 6;
        YearOrMonth yearOrMonth = YearOrMonth.Year;
        Gender gender = Gender.Male;
        int memberId = 1;

        when(catRepository.createCat(any(Cat.class))).thenReturn(1);

        int resultId = catService.addCat(imageUrl, catName, race, age, yearOrMonth, gender, memberId);

        assertEquals(1, resultId);
        verify(catRepository, times(1)).createCat(any(Cat.class));
    }

    @Test
    void addCat_ShouldThrowException_WhenCatNameIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
            catService.addCat("IMG_1428.png", null, Race.Birman, 6, YearOrMonth.Year, Gender.Male, 1)
        );

        verify(catRepository, never()).createCat(any(Cat.class));
    }

    @Test
    void addCat_ShouldThrowException_WhenCatNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                catService.addCat("IMG_1428.png", "", Race.Birman, 6, YearOrMonth.Year, Gender.Male, 1)
        );

        verify(catRepository, never()).createCat(any(Cat.class));
    }

    @Test
    void addCat_ShouldThrowException_WhenRaceIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                catService.addCat("IMG_1428.png", "Whiskers", null, 6, YearOrMonth.Year, Gender.Male, 1)
        );

        verify(catRepository, never()).createCat(any(Cat.class));
    }

    @Test
    void addCat_ShouldThrowException_WhenAgeIsOutOfRange() {
        assertThrows(IllegalArgumentException.class, () ->
                catService.addCat("IMG_1428.png", "Whiskers", Race.Birman, 0, YearOrMonth.Year, Gender.Male, 1)
        );

        assertThrows(IllegalArgumentException.class, () ->
                catService.addCat("IMG_1428.png", "Whiskers", Race.Birman, -1, YearOrMonth.Year, Gender.Male, 1)
        );

        assertThrows(IllegalArgumentException.class, () ->
                catService.addCat("IMG_1428.png", "Whiskers", Race.Birman, 25, YearOrMonth.Year, Gender.Male, 1)
        );

        verify(catRepository, never()).createCat(any(Cat.class));
    }

    @Test
    void addCat_ShouldThrowException_WhenYearOrMonthIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                catService.addCat("IMG_1428.png", "Whiskers", Race.Birman, 6, null, Gender.Male, 1)
        );

        verify(catRepository, never()).createCat(any(Cat.class));
    }

    @Test
    void addCat_ShouldThrowException_WhenGenderIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                catService.addCat("IMG_1428.png", "Whiskers", Race.Birman, 6, YearOrMonth.Year, null, 1)
        );

        verify(catRepository, never()).createCat(any(Cat.class));
    }

    // findCatsByMemberId() - Tests
    @Test
    void findCatsByMemberId_ShouldReturnCats_WhenValidMemberId() {
        int memberId = 1;
        List<Cat> cats = List.of(
                new Cat("IMG_1428.png", "Whiskers", Race.Birman, 6, YearOrMonth.Year, Gender.Male, memberId),
                new Cat("IMG_1429.png", "Luna", Race.Chausie, 3, YearOrMonth.Year, Gender.Female, memberId)
        );

        when(catRepository.findAllCatsByMemberId(memberId)).thenReturn(cats);

        List<Cat> result = catService.findCatsByMemberId(memberId);

        assertEquals(cats, result);
        verify(catRepository, times(1)).findAllCatsByMemberId(memberId);
    }

    @Test
    void findCatsByMemberId_ShouldReturnEmptyList_WhenNoCatsFound() {
        int memberId = 1;

        when(catRepository.findAllCatsByMemberId(memberId)).thenReturn(List.of());

        List<Cat> result = catService.findCatsByMemberId(memberId);

        assertTrue(result.isEmpty());
        verify(catRepository, times(1)).findAllCatsByMemberId(memberId);
    }

    // findCatById() - Tests
    @Test
    void findCatById_ShouldReturnCat_WhenValidCatId() {
        int catId = 1;
        Cat expectedCat = new Cat("IMG_1428.png", "Whiskers", Race.Birman, 6, YearOrMonth.Year, Gender.Male, 1);

        when(catRepository.findCatById(catId)).thenReturn(Optional.of(expectedCat));

        Cat result = catService.findCatById(catId);

        assertEquals(expectedCat, result);
        verify(catRepository, times(1)).findCatById(catId);
    }

    @Test
    void findCatById_ShouldReturnException_WhenNoCatFound() {
        int catId = 1;

        when(catRepository.findCatById(catId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> catService.findCatById(catId));
        verify(catRepository, times(1)).findCatById(catId);
    }
}