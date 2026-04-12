package org.example.racekatteklubben.controller;

import jakarta.servlet.http.HttpSession;
import org.example.racekatteklubben.models.Cat;
import org.example.racekatteklubben.models.Member;
import org.example.racekatteklubben.service.CatService;
import org.example.racekatteklubben.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CatController {
    private final CatService catService;
    public CatController(CatService catService) {
        this.catService = catService;
    }

    @PostMapping("/Cat/delete/{id}")
    public String deleteCat(HttpSession session, @PathVariable int catId) {
        Cat currentCatId = (Cat) session.getAttribute("loggedInMember");
        catService.removeCat(catId);
        if (currentCatId != null && currentCatId.getId() == catId) {
            session.invalidate();
            return "redirect:/login";
        }
        return "redirect:/profile";
    }
}
