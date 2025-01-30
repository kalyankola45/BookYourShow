package com.example.ticketsbooking.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.ticketsbooking.models.Theatrelists;

public class Userdetser implements UserDetails {

    private final Theatrelists theatrelists;

    // Constructor
    public Userdetser(Theatrelists theatrelists) {
        this.theatrelists = theatrelists;
    }

    public Theatrelists getTheatrelists() {
        return theatrelists;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(theatrelists.getRole()));
    }

    @Override
    public String getPassword() {
        return theatrelists.getPassword();
    }

    @Override
    public String getUsername() {
        return theatrelists.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return theatrelists.isAccess();
    }
}
