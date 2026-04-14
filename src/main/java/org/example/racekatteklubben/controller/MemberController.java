package org.example.racekatteklubben.controller;

import jakarta.servlet.http.HttpSession;
import org.example.racekatteklubben.models.Member;
import org.example.racekatteklubben.service.CatService;
import org.example.racekatteklubben.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemberController {
    private final CatService catService;
    private final MemberService memberService;


    public MemberController(CatService catService, MemberService memberService) {
        this.catService = catService;
        this.memberService = memberService;
    }

    @GetMapping("/register")
    public String showSignupForm(Model model) {
        model.addAttribute("member", new Member());
        return "auth/signup";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Member member) {
        memberService.registerMember(member.getMemberName(), member.getEmail(), member.getPassword(), false);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("member", new Member());
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(HttpSession session, Model model, @ModelAttribute Member member) {
        Member loggedIn = memberService.loginMember(member.getEmail(), member.getPassword());

        if (loggedIn != null) {
            session.setAttribute("loggedInMember", loggedIn);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "auth/login";
        }
    }

    @GetMapping("/home")
    public String showHomePage(HttpSession session, Model model) {
        Member member = (Member) session.getAttribute("loggedInMember");

        if (member == null) {
            return "redirect:/login";
        }

        model.addAttribute("member", member);
        return "home";
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        Member member = (Member) session.getAttribute("loggedInMember");

        if (member == null) {
            return "redirect:/login";
        }

        model.addAttribute("member", member);

        return "member/profile";
    }
    
    @GetMapping("/profile/update")
    public String ShowUpdateProfile(HttpSession session, Model model) {
        Member member = (Member) session.getAttribute("loggedInMember");

        if (member == null) {
            return "redirect:/login";
        }

        model.addAttribute("member", member);
        return "member/update";
    }

    @PostMapping("/profile/update")
    public String updateProfile(HttpSession session, Model model, @ModelAttribute Member member, @RequestParam String oldPassword, @RequestParam String password, @RequestParam String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "member/update";
        }

        memberService.updateMember(member.getId(), member.getMemberName(), member.getEmail(), oldPassword, password);
        session.setAttribute("loggedInMember", member);
        return "redirect:/profile";
    }

    @PostMapping("/members/delete/{id}")
    public String deleteMember(HttpSession session, @PathVariable int id) {

        Member currentMember = (Member) session.getAttribute("loggedInMember");
        catService.removeCatByMemberId(id);
        memberService.removeMember(id);

        if (currentMember != null && currentMember.getId() == id) {
            session.invalidate();
            return "redirect:/login";
        }

        return "redirect:/profile";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
