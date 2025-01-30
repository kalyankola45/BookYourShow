package com.example.ticketsbooking.security;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Jwtfilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final Userdetimp userdetimp;

    @Autowired
    public Jwtfilter(JwtProvider jwtProvider, Userdetimp userdetimp) {
        super();
        this.jwtProvider = jwtProvider;
        this.userdetimp = userdetimp;
    }


    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return pathMatcher.match("/user/**", path) ||
               pathMatcher.match("/login/admin", path) ||
               pathMatcher.match("/login/manager", path) ||
               pathMatcher.match("/login/tokenchecker", path) ||
               pathMatcher.match("/login/**", path);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    	
        String jwtString = request.getHeader(Jwtconstant.JWTHEADER);
        if (jwtString == null) {
            throw new NullPointerException("Token is missing");
        }

        if (jwtString != null) {
            String jwt = jwtString.substring(6);

            try {
                String email = jwtProvider.getemailfromtoken(jwt);
               

                if (isemailfromadmin(email)) {
                	
                	if(jwtProvider.istokenvalid(jwt, email)) {
                	
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            email, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    System.err.println("Admin verification done successfully");
                	}
                } else {
                    Userdetser userdetser = (Userdetser) userdetimp.loadUserByUsername(email);
                    if (jwtProvider.istokenvalidstwo(jwt, email, userdetser)) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                email, null, userdetser.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error during token processing: " + e.getMessage());
            }
     filterChain.doFilter(request, response);
           
        }
    }

    private static boolean isemailfromadmin(String givenemail) {
        String adminemail = "admin@gmail.com";
        return adminemail.equals(givenemail);
    }
}
