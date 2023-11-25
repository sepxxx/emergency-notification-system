package com.bnk.recipientssaverntaskresolver.configs;

import com.bnk.recipientssaverntaskresolver.services.JwtService;
import com.bnk.recipientssaverntaskresolver.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
    JwtService jwtService;
    UserDetailsServiceImpl userDetailsService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain){

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
//        log.info("doFilterInternal authHeader {} request.getHeaderNames:{} request: {}", authHeader, request.getHeaderNames(), request);
        log.info("doFilterInternal authHeader {}", authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token, "access");
            log.info("username {}", username);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {//если никто не лежит в контексте
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);//по имени из jwt подгрузим юзера
            log.info("никого в контексте нет userDetails: {}", userDetails);

            if (jwtService.validateToken(token, "access")) {//странно что тут еще одна проверка на совпадение имен,
                log.info("токeн валиден");

                // мы же изначально по нему и грузили UserDetails
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);//если уже кто-то лежит, то класть смысла нет
    }
}
