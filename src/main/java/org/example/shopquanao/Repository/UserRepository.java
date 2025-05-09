package org.example.shopquanao.Repository;

import org.example.shopquanao.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
}
