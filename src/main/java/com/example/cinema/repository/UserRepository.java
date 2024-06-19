package com.example.cinema.repository;

import com.example.cinema.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value = "SELECT u.balance FROM user u where u.id=?1", nativeQuery = true)
    Double findUserBalanceById(long userId);
}
