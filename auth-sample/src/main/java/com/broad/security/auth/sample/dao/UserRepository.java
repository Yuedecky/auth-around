package com.broad.security.auth.sample.dao;

import com.broad.security.auth.sample.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:username)")
    User findByUsernameCaseInsensitive(@Param("username") String username);

    @Query
    User findByEmail(@Param("email") String email);

    @Query
    User findByEmailAndActivationKey(@Param("email") String email, @Param("activationKey") String activationKey);

    @Query
    User findByEmailAndResetPasswordKey(@Param("email") String email,@Param("resetPasswordKey") String resetPasswordKey);

    @Modifying
    @Query("update User set password=:password where username=:username")
    void updatePasswordByNameCaseSensitive(@Param("username") String name,@Param("password") String password);
}
