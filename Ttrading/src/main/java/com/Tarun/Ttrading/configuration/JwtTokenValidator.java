package com.Tarun.Ttrading.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

public class JwtTokenValidator  extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    String jwt = request.getHeader(JwtConstant.JWT_HEADER);
   // bearer token
    if(jwt!=null){
        jwt= jwt.substring(7);

        try{
            SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRETE_KEY.getBytes(StandardCharsets.UTF_8));

            Claims claims = Jwts.parser()  // check once
                    .setSigningKey(key) // Ensure this is the correct method name
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
            String email = String.valueOf(claims.get("email"));
            String authorities = String.valueOf(claims.get("authorities"));

            List<GrantedAuthority> authoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    authoritiesList
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        catch (Exception e){
            throw new RuntimeException("invalid Token");
        }
        filterChain.doFilter(request,response);
    }

    }
}
