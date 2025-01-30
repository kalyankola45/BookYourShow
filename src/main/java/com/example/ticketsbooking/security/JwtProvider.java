package com.example.ticketsbooking.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.ticketsbooking.models.Theatrelists;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;




@Service
public class JwtProvider {
 
	private final SecretKey key = Keys.hmacShaKeyFor(Jwtconstant.SecretKey.getBytes());
	
	public String geneeratetoken(String username) {
		return generatetoken(username);
	}

	private String generatetoken(String username) {
		return Jwts.builder()
				   .setSubject(username)
				   .setIssuedAt(new Date())
				   .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60*10))
				   .signWith(SignatureAlgorithm.HS256 , key)
				   .compact();
	}
	public String getemailfromtoken(String jwt) {
		
		
         try {
        	 Claims claims = Jwts.parser()
                     .setSigningKey(key)
                     .parseClaimsJws(jwt)
                     .getBody();

        	 
              String email = claims.getSubject();
           
              return email;
			
		} catch (Exception e) {
			 System.out.println("Error parsing JWT: " + e.getMessage());
             throw new BadCredentialsException("Invalid token from JWT validator", e);
		}
		
	}
  
	
	public boolean istokenvalidstwo(String token,String email,UserDetails userDetails) {
	
		
		boolean isnameisvalid = email.equals(userDetails.getUsername());
		
		
		
		
		boolean istokenexpired = isexpired(token);
	
		 return isnameisvalid&& !istokenexpired;
	}
	public boolean istokenvalid(String token,String email) {
	
		
		
		boolean isnameisvalid = email.equals(email);
		
		boolean istokenexpired = isexpired(token);
	
		 return isnameisvalid&& !istokenexpired;
	}
	 
	
	public boolean isexpired(String token) {
		
		Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		System.out.println(claims);

		Date date = claims.getExpiration();
		return date.before(new Date());
	}

}
