package com.ghelere.ti.desenvolvimento.repository;

import com.ghelere.ti.desenvolvimento.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByLogin(String login);

    User findUserByLogin(String login);

}
