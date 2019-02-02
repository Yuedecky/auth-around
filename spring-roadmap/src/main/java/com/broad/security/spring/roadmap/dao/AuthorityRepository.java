package com.broad.security.spring.roadmap.dao;

import com.broad.security.spring.roadmap.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority,String> {


}
