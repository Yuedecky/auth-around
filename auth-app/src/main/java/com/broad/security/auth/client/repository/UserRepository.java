package com.broad.security.auth.client.repository;


import com.broad.security.auth.client.domain.ClientUserInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<ClientUserInfo,Long> {

    Optional<ClientUserInfo> findByName(String name);
}
