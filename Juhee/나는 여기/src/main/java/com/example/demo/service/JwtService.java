package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Component
@Slf4j
@Service
public class JwtService {

    @Value("${jwt.salt}")
    private String salt;

    @Value("${jwt.expmin}")
    private Long expireMin;

    @Autowired
    private UserRepository userRepository;

    // 로그인 성공시 JWT 토큰 생성해서 반환
    public String create(final Object obj)
    {
        ObjectMapper objectMapper=new ObjectMapper();
        User user=objectMapper.convertValue(obj,User.class);
        System.err.println("jwt token create");
        final JwtBuilder builder= Jwts.builder();
        builder.setHeaderParam("type","JWT");

        builder.setSubject("로그인토큰")
                .setExpiration(new Date(System.currentTimeMillis()+100*60*expireMin))
                .setIssuedAt(new Date())
                .claim("user_id",user.getId())
                .claim("email",user.getEmail());

        builder.signWith(SignatureAlgorithm.HS256,salt.getBytes());

        final String jwt=builder.compact();
        System.out.println("토큰 발행 : "+jwt);

        return jwt;
    }

    // 전달된 토큰이 제대로 생성된 것인지 확인
    public String checkValid(final String jwt){
        System.out.println("check valid token");
        log.trace("토큰 점검 : {}",jwt);
        Object obj= Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);

        // 토큰 분석
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Map<String, Object>> map = oMapper.convertValue(obj, Map.class);
        System.out.println("토큰 분석 "+map.get("body").get("user_id"));

        String uid=map.get("body").get("user_id").toString();
//        User user=oMapper.convertValue(map.get("body").get("user"),User.class);
//        User user=userRepository.getUserById(uid);

        System.out.println("토큰 정보"+obj.toString());
        return uid;
    }

    // 토큰 분석해 필요한 정보 봔환
    public Map<String,Object> get(final String jwt){
        Jws<Claims> claims=null;

        try{
            claims=Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
        }catch (final Exception e){
            throw new RuntimeException();
        }

        log.trace("claims: {}",claims);

        return claims.getBody();
    }
}