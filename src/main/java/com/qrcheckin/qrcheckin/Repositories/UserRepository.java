package com.qrcheckin.qrcheckin.Repositories;
import com.qrcheckin.qrcheckin.Models.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = {"groups"})
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmailWithGroups(@Param("email") String email);

    Optional<User> findByApiKey(String apiKey);

    boolean existsByEmail(String email);

    boolean existsByApiKey(String apiKey);

}
