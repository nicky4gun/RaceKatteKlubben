package org.example.racekatteklubben.infrastructure;

import org.example.racekatteklubben.models.Cat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class CatRepository implements ICatRepository {

    private JdbcTemplate jdbcTemplate;

    public CatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createCat(Cat cat) {
        String sql = "INSERT INTO cats (image, cat_name, race, age, gender, member_id) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, cat.getImages());
            ps.setString(2, cat.getCatName());
            ps.setString(3, cat.getRace().toString());
            ps.setInt(4, cat.getAge());
            ps.setString(5, cat.getGender());
            ps.setInt(6, cat.getOwnerId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }
}
