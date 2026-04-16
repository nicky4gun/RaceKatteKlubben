package org.example.racekatteklubben.controller;

import jakarta.servlet.http.HttpSession;
import org.example.racekatteklubben.models.Cat;
import org.example.racekatteklubben.models.Member;
import org.example.racekatteklubben.models.enums.Gender;
import org.example.racekatteklubben.models.enums.Race;
import org.example.racekatteklubben.models.enums.YearOrMonth;
import org.example.racekatteklubben.service.CatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CatController {

    private final CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping("/profile/{memberId}/cats")
    public String showCats(HttpSession session, Model model, @PathVariable int memberId) {
        Member member = (Member) session.getAttribute("loggedInMember");

        if (member == null) {
            return "redirect:/login";
        }

        model.addAttribute("member", member);
        model.addAttribute("cats", catService.findCatsByMemberId(memberId));
        return "cat/catList";
    }

    @GetMapping("/profile/{memberId}/cats/create")
    public String showCreateCatForm(@ModelAttribute Cat cat, HttpSession session, Model model, @PathVariable int memberId) {
        Member member = (Member) session.getAttribute("loggedInMember");

        if (member == null) {
            return "redirect:/login";
        }

        model.addAttribute("member", member);
        model.addAttribute("cat", new Cat());
        model.addAttribute("races", Race.values());
        model.addAttribute("yearOrMonths", YearOrMonth.values());
        model.addAttribute("genders", Gender.values());
        return "cat/createCat";
    }

    @PostMapping("/profile/{memberId}/cats/create")
    public String createCat(@ModelAttribute Cat cat, HttpSession session, Model model, @PathVariable int memberId) {
        Member member = (Member) session.getAttribute("loggedInMember");

        if (member == null) {
            return "redirect:/login";
        }

        model.addAttribute("member", member);
        model.addAttribute("cat", cat);


        catService.addCat(cat.getImages(), cat.getCatName(), cat.getRace(), cat.getAge(), cat.getYearOrMonth(), cat.getGender(), member.getId());

        return "redirect:/profile/{memberId}/cats";
    }

    @GetMapping("profile/{memberId}/cats/update/{catId}")
        public String ShowUpdateCatForm(HttpSession session, Model model, @PathVariable int memberId, @PathVariable int catId) {

        Member member = (Member) session.getAttribute("loggedInMember");

        if (member == null) {
            return "redirect:/login";
        }

        Cat cat = catService.findCatById(catId);

        model.addAttribute("member", member);
        model.addAttribute("cat", cat);
        model.addAttribute("races", Race.values());
        model.addAttribute("yearOrMonths", YearOrMonth.values());
        model.addAttribute("genders", Gender.values());

        return "cat/updateCat";

    }

    @PostMapping("profile/{memberId}/cats/update/{catId}")
    public String updateCat(HttpSession session, Model model, @ModelAttribute Cat cat, @PathVariable int memberId, @PathVariable int catId) {
        Member member = (Member) session.getAttribute("loggedInMember");

        if (member == null) {
            return "redirect:/login";
        }

        model.addAttribute("member", member);
        model.addAttribute("cat", cat);


        catService.updateCat(cat.getId(),cat.getImages(),cat.getCatName(),cat.getRace(),cat.getAge(), cat.getYearOrMonth(), cat.getGender(), member.getId());

        return "redirect:/profile/{memberId}/cats";
    }

    @PostMapping("/cat/delete/{catId}")
    public String deleteCat(@PathVariable int catId) {
        catService.removeCat(catId);
        return "redirect:/profile/{memberId}/cats";
    }

    @PostMapping("/home/cats")
    public String searchForCat(HttpSession session, Model model, @ModelAttribute Cat cat, @RequestParam String keyword) {
        Member member = (Member) session.getAttribute("loggedInMember");

        if (member == null) {
            return "redirect:/login";
        }

        model.addAttribute("member", member);

        int memberId = member.getId();
        model.addAttribute("cats", catService.seachForCat(keyword));
        model.addAttribute("cat", cat);
        return "home";
    }
}