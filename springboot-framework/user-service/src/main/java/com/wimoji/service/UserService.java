package com.wimoji.service;

import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.common.JwtTokenUtil;
import com.wimoji.repository.UserRepository;
import com.wimoji.repository.dto.UserEntity;
import com.wimoji.repository.dto.request.UserReq;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private static final boolean LOGIN = true;
    private static final boolean LOGOUT = false;

    public void makeUser(UserReq userReq) {
        UserEntity findUserEntity = repository.findById(userReq.getUid());

        // id 중복 확인
        if(findUserEntity != null){
            throw new GeneralException(Code.ALREADY_USER);
        }

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userReq, UserEntity.class);

        repository.save(userEntity);
    }

    /**
     * 로그인 상태 변경 및 토큰 발급
     * @param id
     * @param password
     * @return
     */
    public HashMap<String, String> loginUser(String id, String password) {

        //로그인 상태 변경 로그인할 때 true
        UserEntity userEntity = repository.findAndModify(id, password, LOGIN);

        if (userEntity == null) {
            throw new GeneralException(Code.NO_USER);
        }

        //로그인 성공, 토큰 발급
        String accessToken = JwtTokenUtil.makeAccessToken(userEntity.getUid(), userEntity.getNickname());
        String refreshToken = JwtTokenUtil.makeRefreshToken(userEntity.getUid(), userEntity.getNickname());

        HashMap<String, String> result = new HashMap<>();
        result.put("accessToken", accessToken); //토큰
        result.put("refreshToken", refreshToken); //토큰
        result.put("nickname", userEntity.getNickname()); //닉네임

        return result;
    }

    /**
     * 로그아웃 상태 변경
     * @param id
     */
    public void logoutUser(String id){
        Query query = new Query();
        Criteria criteria = new Criteria();

        query.addCriteria(criteria.andOperator(
                Criteria.where("uid").is(id)
        ));

        //로그아웃 상태로 변경
        UserEntity userEntity = repository.findAndModify(id, null, LOGOUT);

        if(userEntity == null){
            throw new GeneralException(Code.NO_USER);
        }
    }

    /**
     * 회원 탈퇴
     * @param id
     */
    public void deleteUser(String id){
        UserEntity userEntity = repository.findAndRemove(id);
    }

}
