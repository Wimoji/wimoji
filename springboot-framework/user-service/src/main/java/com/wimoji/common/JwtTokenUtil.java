package com.wimoji.common;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
    private static String secretKey1;
    private static String secretKey2;
    private static Integer expirationTime;
    private static Integer expirationRefreshTime;

    @Autowired
    public JwtTokenUtil(@Value("${jwt.secret1}") String secretKey1,
                        @Value("${jwt.secret2}") String secretKey2,
                        @Value("${jwt.expiration}") Integer expirationTime,
                        @Value("${jwt.expirationRefresh}") Integer expirationRefreshTime) {
        this.secretKey1 = secretKey1;
        this.secretKey2 = secretKey2;
        this.expirationTime = expirationTime;
        this.expirationRefreshTime = expirationRefreshTime;

    }

    /**
     * user id와 user nickname을 포함하는 AccessToken 발급
     * @param uid
     * @param nickname
     * @return
     */
    public static String makeAccessToken(String uid, String nickname){
        //토큰 만료 시간 설정
        Date expires = getTokenExpiration(expirationRefreshTime);

        return Jwts.builder()
                .setSubject(uid)                                //사용자 id
                .setIssuedAt(new Date())                        //현재 시간
                .setExpiration(expires)                         //만기 시간
                .claim("userNickname", nickname)            //사용자 nickname
                .signWith(SignatureAlgorithm.HS512, secretKey1)     //암호화 알고리즘
                .compact();
    }

    /**
     * user id와 user nickname을 포함하는 RefreshToken 발급
     * @param uid
     * @param nickname
     * @return
     */
    public static String makeRefreshToken(String uid, String nickname){
        Date expires = getTokenExpiration(expirationRefreshTime);

        return Jwts.builder()
                .setSubject(uid)                                //사용자 id
                .setIssuedAt(new Date())                        //현재 시간
                .setExpiration(expires)                         //만기 시간
                .claim("userNickname", nickname)            //사용자 nickname
                .signWith(SignatureAlgorithm.HS512, secretKey2)     //암호화 알고리즘
                .compact();
    }

    /**
     * Token 만료 시간 계산
     * @param expirationTime
     * @return
     */
    public static Date getTokenExpiration(int expirationTime){
        Date now = new Date();
        return new Date(now.getTime() + expirationTime);
    }

    /**
     * AccessToken 만료시 RefreshToken 확인 후 재발급
     * @param refreshToken
     * @return
     */
    public String refreshToken(String refreshToken){
        if(validateRefreshToken(refreshToken)){
            throw  new IllegalArgumentException("Invalid refresh token");
        }
        Claims claims = getClaims(refreshToken, secretKey2);
        String userId = claims.getSubject();
        String userNickname = claims.get("userNickname", String.class);
        return makeAccessToken(userId, userNickname);
    }

    private Claims getClaims(String token, String secretKey){
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    /**
     * RefreshToken의 유효성 검증
     * @param token
     * @return
     */
    public boolean validateRefreshToken(String token){
        try {
            Jwts
                    .parserBuilder()
                    .setSigningKey(secretKey2)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            //잘못된 JWT 서명, 잘못된 형식의 JWT
//            e.printStackTrace();
            System.out.println("refresh >> 잘못된 JWT 서명, 잘못된 형식의 JWT");
        }catch (ExpiredJwtException e){
            //만료된 JWT 토큰
//            e.printStackTrace();
            System.out.println("refresh >> 만료된 JWT 토큰");
        }catch (UnsupportedJwtException e){
            //지원되지 않는 JWT 토큰
//            e.printStackTrace();
            System.out.println("refresh >> 지원되지 않는 JWT 토큰");
        }catch (IllegalArgumentException e){
            //JWT가 비어있음
//            e.printStackTrace();
            System.out.println("refresh >> JWT가 비어있음");
        }
        return false;
    }

    /**
     * AccessToken의 유효성 검증
     * @param token
     * @return
     */
    public boolean validateToken(String token){
        try{
            Jwts
                    .parserBuilder()
                    .setSigningKey(secretKey1)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            //잘못된 JWT 서명, 잘못된 형식의 JWT
//            e.printStackTrace();
            System.out.println("access >> 잘못된 JWT 서명, 잘못된 형식의 JWT");
        }catch (ExpiredJwtException e){
            //만료된 JWT 토큰
//            e.printStackTrace();
            System.out.println("access >> 만료된 JWT 토큰");
        }catch (UnsupportedJwtException e){
            //지원되지 않는 JWT 토큰
//            e.printStackTrace();
            System.out.println("access >> 지원되지 않는 JWT 토큰");
        }catch (IllegalArgumentException e){
            //JWT가 비어있음
//            e.printStackTrace();
            System.out.println("access >> JWT가 비어있음");
        }
        return false;
    }

}
