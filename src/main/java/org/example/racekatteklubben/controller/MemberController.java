package org.example.racekatteklubben.controller;

import jakarta.servlet.http.HttpSession;
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
    public String login(HttpSession session, Model model, @ModelAttribute Member member) {
        Member loggedIn = memberService.loginMember(member.getEmail(), member.getPassword());

        if (loggedIn != null) {
            session.setAttribute("loggedInMember", loggedIn);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
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

        return "profile";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        session.invalidate();
        return "redirect:/login";
    }
}
