package com.shop.shoppingmall.domain.repository;

import com.shop.shoppingmall.domain.entity.UserEntity;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserRepository {

    private final SqlSessionTemplate sql;

    @Autowired
    public UserRepository(SqlSessionTemplate sql) {
        this.sql = sql;
    }

    public UserEntity findById(String email) {
        return sql.selectOne("ShoppingMall.findById", email);
    }

    public void joinUser(UserEntity userEntity) {
        sql.update("ShoppingMall.joinUser", userEntity);
    }

    public void editUser(Map<String, Object> map) {
        sql.update("ShoppingMall.editUser", map);
    }
}
