package org.example.racekatteklubben.controller;

import org.example.racekatteklubben.models.Member;
import org.example.racekatteklubben.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String showLoginForm(Model model) {
        model.addAttribute("member", new Member());
        return "login";
    }

    @GetMapping("/register")
    public String showSignupForm(Model model) {
        model.addAttribute("member", new Member());
        return "signup";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Member member) {
        memberService.registerMember(member.getMemberName(), member.getEmail(), member.getPassword(), false);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Member member, Model model) {
        boolean loggedIn = memberService.loginMember(member);

        if (loggedIn) {
            model.addAttribute("member", member);
            return "home";
        } else {
            model.addAttribute("member", new Member());
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        return "profile";
    }
}
