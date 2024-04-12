package com.shop.shoppingmall.service;

import com.shop.shoppingmall.controller.dto.userDto.UserEditDto;
import com.shop.shoppingmall.controller.dto.userDto.UserJoinDto;
import com.shop.shoppingmall.controller.dto.userDto.UserLoginDto;
import com.shop.shoppingmall.domain.entity.UserEntity;
import com.shop.shoppingmall.domain.repository.UserRepository;
import com.shop.shoppingmall.utils.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity login(UserLoginDto userLoginDto) {
        UserEntity entity = userRepository.findById(userLoginDto.getEmail());
        // 정보 확인
        if(entity == null){
            throw new RuntimeException("가입된 정보가 없습니다.");
        }

        Encryption encryption = new Encryption();
        String encryptPwd = encryption.getEncrypt(userLoginDto.getPassword(), encryption.salt);

        if(!entity.getPassword().equals(encryptPwd)) {
            throw new RuntimeException("비밀번호가 다릅니다.");
        }

        return userRepository.findById(userLoginDto.getEmail());
    }

    public void joinUser(UserJoinDto dto) {
        // 아이디 중복 확인
        if(userRepository.findById(dto.getEmail()) != null) {
            throw new RuntimeException("이미 가입된 이메일 입니다.");
        }
        // 비밀번호 확인
        if(!dto.getPassword().equals(dto.getConfirmPwd())) {
            throw new RuntimeException("비밀번호가 서로 다릅니다.");
        }
        Encryption encryption = new Encryption();
        String encryptPwd = encryption.getEncrypt(dto.getPassword(), encryption.salt);
        userRepository.joinUser(new UserEntity(dto.getEmail(), encryptPwd, dto.getName(), dto.getPhone(), dto.getAddress()));
    }

    public UserEntity findById(String id) {
        return userRepository.findById(id);
    }

    public void editUser(UserEditDto dto, String id) {
        Encryption encryption = new Encryption();
        String encryptPwd = encryption.getEncrypt(dto.getPassword(), encryption.salt);

        UserEditDto dto1 = new UserEditDto(dto.getName(), encryptPwd, dto.getPhone(), dto.getAddress());
        Map<String, Object> map = new HashMap<>();
        map.put("name", dto1.getName());
        map.put("phone", dto1.getPhone());
        map.put("address", dto1.getAddress());
        map.put("password", dto1.getPassword());
        map.put("email", id);

        userRepository.editUser(map);
    }
}
