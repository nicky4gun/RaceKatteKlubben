package org.example.racekatteklubben.infrastructure;

import org.example.racekatteklubben.models.Member;

public interface IMemberRepository {
    int createMember(Member member);

    Member findMemberByEmail(String email);

    void updateMember(Member member);

    void removeMember(int id);
}
