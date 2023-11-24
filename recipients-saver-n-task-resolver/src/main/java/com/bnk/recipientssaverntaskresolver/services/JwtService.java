package com.bnk.recipientssaverntaskresolver.services;

import com.bnk.recipientssaverntaskresolver.dtos.responses.JwtResponseDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.lang.NonNull;
import jakarta.security.auth.message.AuthException;
import lombok.extern.slf4j.Slf4j;
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

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    private final Map<String, String> refreshStorage = new HashMap<>();

    public void putToRefreshStorage(String key, String val) {
        refreshStorage.put(key, val);
    };
    public JwtResponseDto refreshAccessAndRefreshToken(@NonNull String refreshToken) throws AuthException {
        if (validateToken(refreshToken)) {
            final Claims claims = extractAllClaims(refreshToken);
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

    public JwtResponseDto  refreshAccessToken(@NonNull String refreshToken) {
        if (validateToken(refreshToken)) {
            String username = extractUsername(refreshToken);
            String savedRefreshToken = refreshStorage.get(username);
            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                String accessToken = generateAccessToken(username);
                return new JwtResponseDto(accessToken, null);
            }
        }
        return new JwtResponseDto(null, null);
    }

    public String generateAccessToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        //почему здесь не добавляются роли юзера????
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    public String generateRefreshToken(String userName) {

        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 30L*24*60*60*1000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

//    public Boolean validateToken(String token) {
//        return !isTokenExpired(token);
//    }
    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
//        } catch (SignatureException sEx) {
//            log.error("Invalid signature", sEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }


}
