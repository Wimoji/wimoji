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

    /**
     * 신규 유저 생성
     * @param : uid, nickname, password
     * @return :
     */
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
    public void removeUser(String id){
        repository.findAndRemove(id);
    }

    /**
     * DB의 유저 정보 반환
     * @param id
     * @return UserEntity
     */
    public List<String> getChatListByUser(String id) {
        UserEntity user = repository.findById(id);
        if(user == null) {
            throw new GeneralException(Code.NO_USER);
        }

        return user.getChatList();
    }

    /**
     * 유저 정보에 채팅 추가
     * @param : user의 id, 채팅방의 id
     * @return :
     */
    public void makeChat(String uid, String rid) {
        repository.makeChat(uid, rid);
    }

    /**
     * 유저 정보에 채팅 삭제
     * @param : user의 id, 채팅방의 id
     * @return :
     */
    public void removeChat(String uid, String rid) {
        UserEntity user = repository.findById(uid);
        if(user == null) {
            throw new GeneralException(Code.NO_USER);
        }

        List<String> chatList = user.getChatList();
        if(chatList == null) {
            throw new GeneralException(Code.NOT_FOUND);
        }

        int idx = chatList.indexOf(rid);
        if(idx == -1) {
            throw new GeneralException(Code.NOT_FOUND);
        }

        repository.removeChat(uid, rid);
    }
}