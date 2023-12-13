package com.engima.shopeymart.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.engima.shopeymart.entity.AppUser;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    //generateToken
    //getDataByUsername
    //validation
    private final String jwtSecret = "secret";

    private final String appName = "shoppey Mart App";

    public String generateToken(AppUser appUser){
        try{
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
            String token = JWT.create()
                    .withIssuer(appName) //info untuk app nama yang dibuat
                    .withSubject(appUser.getId()) //menentukan object yang akan dibuat, biasanya dari id
                    .withExpiresAt(Instant.now().plusSeconds(60))//menentukan waktu kadaluarsa token nanti, dalam sini kadaluarsa adalah 60 detik setelah dibuat
                    .withIssuedAt(Instant.now()) //menetapkan waktu token kapan dibuat
                    .withClaim("role", appUser.getRole().name()) //menambahkan claim atau info nama pengguna
                    .sign(algorithm); // intinya seperti ttd kontrak bahwa algoritma yang dipakai sudah pasti HMAC256
            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException();
        }
    }
    public boolean verifyJwtToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
            JWTVerifier verifier = JWT.require(algorithm).build(); // verifikasi algoritma sudah sesuai atau belum
            DecodedJWT decodedJWT = verifier.verify(token); //dibalikin di decode lagi dan di verif lagi tokennya sudah benar apa belum
            return decodedJWT.getIssuer().equals(appName);
        }catch (JWTVerificationException e){
            throw new RuntimeException();
        }
    }

    public Map<String, String> getUserInfoByToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            Map<String, String> userInfo = new HashMap<>(); //untuk map data userInfo
            userInfo.put("userId", decodedJWT.getSubject()); //untuk ambil info userid sama role
            userInfo.put("role", decodedJWT.getClaim("role").asString());
            return userInfo;
        }catch (JWTVerificationException e){
            throw new RuntimeException();
        }

    }
}
