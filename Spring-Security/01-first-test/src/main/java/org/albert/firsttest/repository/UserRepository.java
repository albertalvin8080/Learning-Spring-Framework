package org.albert.firsttest.repository;

import org.albert.firsttest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
        SELECT u FROM User u WHERE u.name = :name
    """)
    Optional<User> findByName(String name);
}
