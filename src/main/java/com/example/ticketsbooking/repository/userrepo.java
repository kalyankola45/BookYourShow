package com.example.ticketsbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ticketsbooking.models.Users;

@Repository
public interface userrepo extends JpaRepository<Users, Integer> {

}
