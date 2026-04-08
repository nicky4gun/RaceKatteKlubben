package org.example.racekatteklubben.service;

import org.example.racekatteklubben.infrastructure.IMemberRepository;
import org.example.racekatteklubben.models.Member;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final IMemberRepository memberRepository;

    public MemberService(IMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void registerMember(String memberName, String email, String password, boolean admin) {
        validateMember(memberName, email, password);
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        Member member = new Member(memberName, email, hashedPassword, admin);
        memberRepository.createMember(member);
    }

    public boolean loginMember(Member member) {
        Member existingMember = memberRepository.findMemberByEmail(member.getEmail());
        if (existingMember == null) return false;

        return BCrypt.checkpw(member.getPassword(), existingMember.getPassword());
    }

    public void updateMember(Member member) {
        memberRepository.updateMember(member);
    }

    public void removeMember(int id) {memberRepository.removeMember(id);}

    private void validateMember(String memberName, String email, String password) {
        if (memberName == null || memberName.isEmpty()) {
            throw new IllegalArgumentException("Please enter your name");
        }

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Please enter your email");
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]{2,}$")) {
            throw new IllegalArgumentException("Invalid email address");
        }

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Please enter your password");
        }

        if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d).{8,}$")) {
            throw new IllegalArgumentException("Invalid password");
        }
    }
}
