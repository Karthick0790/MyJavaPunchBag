package com.example.jwtDemo.auth;

import com.example.jwtDemo.service.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestTokenHeader = request.getHeader("Authorization");

        if(!Strings.isEmpty(requestTokenHeader) && requestTokenHeader.startsWith("Bearer ")) {
            String jwtToken = requestTokenHeader.substring("Bearer ".length());
            try {
                String username = jwtTokenUtil.getUsername(jwtToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if(jwtTokenUtil.validate(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT token");
            }
            catch (ExpiredJwtException e) {
                System.out.println("JWT token has expired");
            }
        }
        else {
            logger.warn("JWT token does not begin with Bearer string");
        }
        filterChain.doFilter(request, response);
    }
}
