package com.example.ticketsbooking.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.ticketsbooking.models.Theatrelists;
import com.example.ticketsbooking.repository.Theatrelistrepo;


@Component
public class Userdetimp implements UserDetailsService {

    private final Theatrelistrepo theatrelistrepo;

    @Autowired
    public Userdetimp(Theatrelistrepo theatrelistrepo) {
        this.theatrelistrepo = theatrelistrepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Theatrelists theatrelists = theatrelistrepo.findByEmail(email);
        if (theatrelists == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return  new Userdetser(theatrelists);
    }
}
