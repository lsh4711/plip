package com.server.domain.mail.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.server.domain.mail.entity.AuthMailCode;

//CrudRepository를 상속받은 RedisRepository를 사용함
@Repository
public interface AuthMailCodeRepository extends CrudRepository<AuthMailCode, String> {
    Optional<AuthMailCode> findByEmail(String email);
}
