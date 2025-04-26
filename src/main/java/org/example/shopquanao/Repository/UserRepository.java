package org.example.shopquanao.Repository;

import org.example.shopquanao.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
