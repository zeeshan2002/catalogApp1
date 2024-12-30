package com.zeeshan.catalog.repository;

import com.zeeshan.catalog.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {
    Users getUserByUsername(String username);
}
