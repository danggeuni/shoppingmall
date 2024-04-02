package com.shop.shoppingmall.domain.repository;

import com.shop.shoppingmall.domain.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserEntity findById(String email) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM USER WHERE EMAIL = ?", new Object[]{email}, userLoginDtoRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void joinUser(UserEntity userEntity) {
        jdbcTemplate.update("INSERT INTO USER (EMAIL, PASSWORD, NAME, PHONE, ADDRESS) VALUES (?, ?, ?, ?, ?)", userEntity.getEmail(), userEntity.getPassword(), userEntity.getName(), userEntity.getPhone(), userEntity.getAddress());
    }

    RowMapper<UserEntity> userLoginDtoRowMapper() {
        return (rs, rowNum) -> new UserEntity(
                rs.getString("EMAIL"),
                rs.getString("PASSWORD"),
                rs.getString("NAME"),
                rs.getString("PHONE"),
                rs.getString("ADDRESS")
        );
    }

}
