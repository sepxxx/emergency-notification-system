package com.bnk.recipientssaverntaskresolver.services;

import com.bnk.recipientssaverntaskresolver.dtos.responses.JwtResponseDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.lang.NonNull;
import jakarta.annotation.PostConstruct;
import jakarta.security.auth.message.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtService {


    @Value("${jwt.access-token.secret}")
    String accessTokenSecret ;
    @Value("${jwt.refresh-token.secret}")
    String refreshTokenSecret ;
    @Value("${jwt.refresh-token.live-time-in-days}")
    int refreshTokenExpirationDays;
    @Value("${jwt.access-token.live-time-in-mins}")
    int accessTokenExpirationMins;

    Long refreshTokenExpirationMillis;
    Long accessTokenExpirationMillis;
    @PostConstruct
    public void init() {
        refreshTokenExpirationMillis = refreshTokenExpirationDays * 24 * 60 * 60 * 1000L;
        accessTokenExpirationMillis = accessTokenExpirationMins * 60 * 1000L;
    }

    private final Map<String, String> refreshStorage = new HashMap<>();

    public void putToRefreshStorage(String key, String val) {
        refreshStorage.put(key, val);
    };
    public JwtResponseDto refreshAccessAndRefreshToken(@NonNull String refreshToken) throws AuthException {
        if (validateToken(refreshToken, refreshTokenSecret)) {
            final Claims claims = extractAllClaims(refreshToken, "refresh");
            final String username = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(username);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final String accessToken = generateAccessToken(username);
                final String newRefreshToken = generateRefreshToken(username);
                refreshStorage.put(username, newRefreshToken);
                return new JwtResponseDto(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный resfresh токен");
    }

    public String  refreshAccessToken(@NonNull String refreshToken) {
        if (validateToken(refreshToken, refreshTokenSecret)) {
            log.info("refreshAccessToken validateToken");

            String username = extractUsername(refreshToken, "refresh");
            String savedRefreshToken = refreshStorage.get(username);
            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                return generateAccessToken(username);
//                return new JwtResponseDto(accessToken, null);
            }
        }
        return null;
//        return new JwtResponseDto(null, null);
    }

    public String generateAccessToken(String userName) {
        log.info("generateAccessToken issued:{}, exp:{}", new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis() + accessTokenExpirationMillis) );
        log.info("accessTokenExpirationMillis:{}", accessTokenExpirationMillis);

        Map<String, Object> claims = new HashMap<>();
        //почему здесь не добавляются роли юзера????
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationMillis))
                .signWith(getSignKey(accessTokenSecret), SignatureAlgorithm.HS256).compact();
    }

    public String generateRefreshToken(String userName) {

        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMillis))
                .signWith(getSignKey(refreshTokenSecret), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey(String secret) {
        byte[] keyBytes= Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token,String tokenType) {
        return extractClaim(token, Claims::getSubject, tokenType);
    }

    public Date extractExpiration(String token, String tokenType) {
        return extractClaim(token, Claims::getExpiration, tokenType);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, String tokenType) {
        final Claims claims = extractAllClaims(token, tokenType);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, String type) {
        String secret = chooseSecret(type);

        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey(secret))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token, String tokenType) {
        return extractExpiration(token, tokenType).before(new Date(System.currentTimeMillis()));
    }

    public Boolean validateToken(String token, String tokenType) {
        return !isTokenExpired(token, tokenType);
    }

    private String chooseSecret(String type) {
        String secret;

        if(type.equals("access"))
            secret = accessTokenSecret;
        else
            secret = refreshTokenSecret;
        return secret;
    }
//    public Boolean validateToken(String token, String type) {
//        String secret = chooseSecret(type);
//        try {
//            Jwts.parserBuilder()
//                    .setSigningKey(secret)
//                    .build()
//                    .parseClaimsJws(token);
//            return true;
//        } catch (ExpiredJwtException expEx) {
//            log.error("Token expired", expEx);
//        } catch (UnsupportedJwtException unsEx) {
//            log.error("Unsupported jwt", unsEx);
//        } catch (MalformedJwtException mjEx) {
//            log.error("Malformed jwt", mjEx);
////        } catch (SignatureException sEx) {
////            log.error("Invalid signature", sEx);
//        } catch (Exception e) {
//            log.error("invalid token", e);
//        }
//        return false;
//    }


}
