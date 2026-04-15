package org.example.racekatteklubben.infrastructure;

import org.example.racekatteklubben.models.Cat;
import org.example.racekatteklubben.models.SearchMemberDto;
import org.example.racekatteklubben.models.enums.Gender;
import org.example.racekatteklubben.models.enums.Race;
import org.example.racekatteklubben.models.enums.YearOrMonth;
import org.example.racekatteklubben.models.interfaces.IMemberRepository;
import org.example.racekatteklubben.models.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class MemberRepository implements IMemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createMember(Member member) {
        String sql = "INSERT INTO members (member_name, email, password, admin) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, member.getMemberName());
            ps.setString(2, member.getEmail());
            ps.setString(3, member.getPassword());
            ps.setBoolean(4, member.isAdmin());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public Member findMemberByEmail(String email) {
        String sql = "SELECT id, member_name, email, password, admin FROM members WHERE email = ?";

        List<Member> members = jdbcTemplate.query(sql, (rs, rowNum) ->
                new Member(
                        rs.getInt("id"),
                        rs.getString("member_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getBoolean("admin")
                ), email);

        return members.isEmpty() ? null : members.getFirst();
    }

    @Override
    public Member findMemberById(int id) {
        String sql = "SELECT id, member_name, email, password, admin FROM members WHERE id = ?";

        List<Member> members = jdbcTemplate.query(sql, (rs, rowNum) ->
                new Member(
                        rs.getInt("id"),
                        rs.getString("member_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getBoolean("admin")
                ), id);

        return members.isEmpty() ? null : members.getFirst();
    }

    @Override
    public List<SearchMemberDto> searchForMember(String keyword) {
        String sql = "SELECT id, member_name FROM members WHERE member_name LIKE ?";

        String pattern = "%" + keyword + "%";

        return jdbcTemplate.query(sql, new Object[]{pattern}, (rs,rowNum) ->
                new SearchMemberDto(
                        rs.getInt("id"),
                        rs.getString("member_name")
                ));
    }

    @Override
    public void updateMember(Member member) {
        String sql = "UPDATE members SET member_name = ?, email = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(sql, member.getMemberName(), member.getEmail(), member.getPassword(), member.getId());
    }

    @Override
    public void removeMember(int id){
        String sql = "DELETE FROM members WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
