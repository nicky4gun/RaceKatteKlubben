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
        if (memberRepository.findMemberByEmail(email) != null) return;
        validateMemberName(memberName);
        validateEmail(email);
        validatePassword(password);
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        Member member = new Member(memberName, email, hashedPassword, admin);
        memberRepository.createMember(member);
    }

    public Member loginMember(String email, String password) {
        Member existingMember = memberRepository.findMemberByEmail(email);

        if (existingMember != null &&  BCrypt.checkpw(password, existingMember.getPassword())) {
            return existingMember;
        }

        return null;
    }

    public void updateMember(int id, String memberName, String email, String oldPassword, String password) {
        Member existingMember = memberRepository.findMemberById(id);

        if (existingMember == null) {
            throw new IllegalArgumentException("Member with id " + id + " does not exist");
        }

        validateMemberName(memberName);
        validateEmail(email);

        String hashedPassword = resolvePassword(oldPassword, password, existingMember.getPassword());
        Member updatedMember = new Member(id, memberName, email, hashedPassword, false);
        memberRepository.updateMember(updatedMember);
    }

    private String resolvePassword(String password, String oldPassword, String existingHashedPassword) {
        if (password != null && !password.isEmpty()) {
            validatePassword(password);

            if (!BCrypt.checkpw(oldPassword, existingHashedPassword)) {
                throw new IllegalArgumentException("Old password is wrong");
            }

            return BCrypt.hashpw(password, BCrypt.gensalt(12));
        }

        return existingHashedPassword;
    }

    public void removeMember(int id) {memberRepository.removeMember(id);}

    private void validateMemberName(String memberName) {
        if (memberName == null || memberName.isEmpty()) {
            throw new IllegalArgumentException("Please enter your name");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Please enter your email");
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]{2,}$")) {
            throw new IllegalArgumentException("Invalid email address");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Please enter your password");
        }

        if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d).{8,}$")) {
            throw new IllegalArgumentException("Invalid password");
        }
    }
}