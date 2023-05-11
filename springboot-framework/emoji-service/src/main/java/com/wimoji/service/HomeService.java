package com.wimoji.service;

import com.wimoji.repository.Entity.Emoji;
import com.wimoji.repository.Entity.User;
import com.wimoji.repository.UserRepository;
import com.wimoji.repository.dto.request.HomeReq;
import com.wimoji.repository.dto.response.HomeRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HomeService {
    private final UserRepository userRepository;

    /**
     * 현재 위치 기준으로 반경 100m이내 30개의 이모지 가져오기
     * @param uid
     * @param homeReq
     * @return
     */
    public List<HomeRes> getOtherEmojiList(String uid, HomeReq homeReq){
        /*
        1. 본인을 제외한 현재 있는 모든 유저 중 로그인한 사람들
        2. 유저의 이모지 리스트 가져오기
        3. 동코드가 같은 친구 들고오기
        4. 위도 경도 계산
        5. 해당되는 친구 저장
        6. 거리순 정렬하여 30개 뽑기
         */
        //1. 본인을 제외한 현재 있는 모든 유저 중 로그인한 사람들
        List<User> userList = userRepository.findLoginedUser(uid);
        //반환할 list
        List<HomeRes> list = new ArrayList<>();

        //2. 유저의 이모지 리스트 가져오기
        for(User user: userList){
            List<Emoji> emojiList = user.getEmoji();
            //이모지 리스트가 없으면 다음 유저로
            if(emojiList == null)
                continue;


            //3. 동 코드가 같은 이모지 리스트 가져오기
            for(Emoji emoji: emojiList){
//                if(!emoji.getDongCode().equals(homeReq.getDongCode()))
//                    continue;

                //4. 위도 경도로 거리 계산
                double dis = getDistance(Double.parseDouble(homeReq.getLatitude()), Double.parseDouble(homeReq.getLongitude()),
                        Double.parseDouble(emoji.getLatitude()), Double.parseDouble(emoji.getLongitude()));

                log.info("Distance >> " + dis);
                //반경 600m 넘으면 추가 안 함
                if(dis > 600)
                    continue;

                //5. 저장
                ModelMapper mapper = new ModelMapper();
                mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
                HomeRes homeRes = mapper.map(emoji, HomeRes.class);
                homeRes.setDis(dis);
                homeRes.setUid(user.getUid());
                list.add(homeRes);
            }//emojiList
        }//userList

        //6. 거리순 정렬하여 30개 뽑기
        Collections.sort(list, new Comparator<HomeRes>() {
            @Override
            public int compare(HomeRes o1, HomeRes o2) {
                if(o1.getDis() >= o2.getDis())
                    return 1;
                else
                    return -1;
            }
        });

        if(list.size() < 30)
            return list;
        else
            return list.subList(0, 30);
    }

    /**
     * 거리 구하기
     * @param lat1
     * @param long1
     * @param lat2
     * @param long2
     * @return
     */
    public double getDistance(double lat1, double long1, double lat2, double long2){
        double EARTH_RADIUS = 6371000;//6371km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(long2 - long1);

        double a = Math.sin(dLat/2)* Math.sin(dLat/2)+ Math.cos(Math.toRadians(lat1))* Math.cos(Math.toRadians(lat2))* Math.sin(dLon/2)* Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = EARTH_RADIUS * c * 1000;    // Distance in m
        return d;
    }
}
