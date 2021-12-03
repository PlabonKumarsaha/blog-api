package com.pks.blog.repository;

import com.pks.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User>findByEmail(String email);

    @Query(value = "SELECT * FROM blog.users where users.name = ?1 OR users.email = ?2 ; ",nativeQuery = true)
    Optional<User>findByUserNameOrEmailId(String userName, String email);

    Optional<User>findByUserName(String userName);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);
}
