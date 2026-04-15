package org.example.racekatteklubben.models;

public class SearchMemberDto {
    private int id;
    private String memberName;


    public SearchMemberDto(int id, String memberName) {
        this.id = id;
        this.memberName = memberName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
