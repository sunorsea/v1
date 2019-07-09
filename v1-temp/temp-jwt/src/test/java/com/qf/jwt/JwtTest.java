package com.qf.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/28
 */
public class JwtTest {

    @Test
    public void createTokenTest() {
        JwtBuilder jwtBuilder= Jwts.builder()
                .setId("111")
                .setSubject("rhb")
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+180*1000))
                .signWith(SignatureAlgorithm.HS256,"java1902");
        String jwtToken = jwtBuilder.compact();
        System.out.println(jwtToken);
    }

    @Test
    public void parseToken() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMTEiLCJzdWIiOiJyaGIiLCJpYXQiOjE1NjE2OTMwOTMsImV4cCI6MTU2MTY5MzI3M30.YQ-znCr6eH1P5zf0xZWh_hRnx--AilzzGl8sAbP7-WU";
        Claims claims =
                Jwts.parser().setSigningKey("java1902").parseClaimsJws(token).getBody();
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(claims.getExpiration());
    }
}
