package com.AbnerTest.ecommerce_test.elements.Users;

import com.AbnerTest.ecommerce_test.core.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByActiveTrueAndLogin(String login);
    List<Users> findAllByActiveTrue();
}
