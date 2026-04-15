package org.example.racekatteklubben.models.interfaces;

import org.example.racekatteklubben.models.Member;
import org.example.racekatteklubben.models.SearchMemberDto;

import java.util.List;

public interface IMemberRepository {
    int createMember(Member member);

    Member findMemberByEmail(String email);

    Member findMemberById(int memberId);

    void updateMember(Member member);

    void removeMember(int id);

    List<SearchMemberDto> searchForMember(String keyword);
}
