package org.example.racekatteklubben.infrastructure;

import org.example.racekatteklubben.models.Cat;
import org.example.racekatteklubben.models.enums.Gender;
import org.example.racekatteklubben.models.enums.Race;
import org.example.racekatteklubben.models.enums.YearOrMonth;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class CatRepository implements ICatRepository {

    private JdbcTemplate jdbcTemplate;

    public CatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createCat(Cat cat) {
        String sql = "INSERT INTO cats (image, cat_name, race, age, year_or_month, gender, member_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, cat.getImages());
            ps.setString(2, cat.getCatName());
            ps.setString(3, cat.getRace().toString());
            ps.setInt(4, cat.getAge());
            ps.setString(5, cat.getYearOrMonth().toString());
            ps.setString(6, cat.getGender().toString());
            ps.setInt(7, cat.getOwnerId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public List<Cat> findAllCatsByMemberId(int memberId) {
        String sql = "SELECT id, image, cat_name, race, age,year_or_month, gender, member_id FROM cats WHERE member_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Cat(
                        rs.getInt("id"),
                        rs.getString("image"),
                        rs.getString("cat_name"),
                        Race.valueOf(rs.getString("race")),
                        rs.getInt("age"),
                        YearOrMonth.valueOf(rs.getString("year_or_month")),
                        Gender.valueOf(rs.getString("gender")),
                        rs.getInt("member_id")
                ), memberId);
    }

    @Override
    public Optional<Cat> findCatById(int id) {
        String sql = "SELECT id, image, cat_name, race, age, year_or_month, gender, member_id FROM cats WHERE id = ?";

        List<Cat> cats = jdbcTemplate.query(sql, (rs, rowNum) ->
                new Cat(
                        rs.getInt("id"),
                        rs.getString("image"),
                        rs.getString("cat_name"),
                        Race.valueOf(rs.getString("race")),
                        rs.getInt("age"),
                        YearOrMonth.valueOf(rs.getString("year_or_month")),
                        Gender.valueOf(rs.getString("gender")),
                        rs.getInt("member_id")
                ), id);

        return cats.isEmpty() ? Optional.empty() : Optional.of(cats.getFirst());
    }

    @Override
    public void updateCat(Cat cat) {
        String sql = "UPDATE cats SET image = ?, cat_name = ?, race = ?, age = ?, year_or_month = ?, gender = ?, member_id = ? WHERE id = ?";

        jdbcTemplate.update(sql,
                cat.getImages(),
                cat.getCatName(),
                cat.getRace().toString(),
                cat.getAge(),
                cat.getYearOrMonth().toString(),
                cat.getGender().toString(),
                cat.getOwnerId(),
                cat.getId()
        );
    }

    @Override
    public void deleteCat(int catId) {
        String sql = "DELETE FROM cats WHERE id = ?";
        jdbcTemplate.update(sql, catId);
    }

    @Override
    public void deleteCatByMemberId(int memberId){
        String sql = "DELETE FROM cats WHERE member_id = ?";
        jdbcTemplate.update(sql, memberId);
    }
}
