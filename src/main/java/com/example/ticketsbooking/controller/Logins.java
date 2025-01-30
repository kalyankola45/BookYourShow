package com.example.ticketsbooking.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticketsbooking.models.Login;
import com.example.ticketsbooking.security.JwtProvider;
import com.example.ticketsbooking.security.Userdetimp;
import com.example.ticketsbooking.security.Userdetser;

@RestController
@RequestMapping("/login")
public class Logins {

    private final AuthenticationManager adminAuthentication;
    private final AuthenticationManager managerAuthentication;
    private final JwtProvider jwtProvider;
    private final Userdetimp userdetimp;

    @Autowired
    public Logins(@Qualifier("adminAuthenticationManager") AuthenticationManager adminAuthentication,
            @Qualifier("managerAuthenticationManager") AuthenticationManager managerAuthentication,
			JwtProvider jwtProvider, Userdetimp userdetimp) {
		super();
		this.adminAuthentication = adminAuthentication;
		this.managerAuthentication = managerAuthentication;
		this.jwtProvider = jwtProvider;
		this.userdetimp = userdetimp;
	}
    
    @PostMapping("/admin")
    public ResponseEntity<?> adminLogin(@RequestBody Login login) {
        return authenticate(login, adminAuthentication);
    }



	@PostMapping("/manager")
    public ResponseEntity<?> managerLogin(@RequestBody Login login) {
    	System.err.println("login proccess start");
        return authenticate(login, managerAuthentication);
    }

    private ResponseEntity<?> authenticate(Login login, AuthenticationManager authenticationManager) {
        try {
        	System.err.println("process  start");
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtProvider.geneeratetoken(login.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }
    @PostMapping("/mantokencheker")
    private ResponseEntity<?> checkmantoken(@RequestBody String token) {
        System.out.println("Manager token checking process start");
        try {
          
            

            String email = jwtProvider.getemailfromtoken(token);
           
            Userdetser userdetser = (Userdetser) userdetimp.loadUserByUsername(email);

            Boolean resBoolean = jwtProvider.istokenvalidstwo(token, email, userdetser);
          

            return ResponseEntity.ok(resBoolean);
        } catch (Exception e) {
            System.out.println("Error during token validation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }

    
    @PostMapping("/admintokenchecker")
    private ResponseEntity<?> checkthetoken(@RequestBody String token){
    	System.err.println("token cheker process start");
    	try {
    		

            String email = jwtProvider.getemailfromtoken(token);
           
            boolean isValid = jwtProvider.istokenvalid(token, email);
            
           
            return ResponseEntity.ok(isValid);
        } catch (Exception e) {
            // Handle any errors, such as invalid token
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }
}
