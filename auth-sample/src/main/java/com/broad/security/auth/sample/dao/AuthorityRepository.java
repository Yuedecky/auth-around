package com.broad.security.auth.sample.dao;

import com.broad.security.auth.sample.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority,String> {


}
