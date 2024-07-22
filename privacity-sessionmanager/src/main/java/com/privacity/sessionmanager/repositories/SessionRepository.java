package com.privacity.sessionmanager.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.privacity.sessionmanager.model.Session;

public interface SessionRepository extends MongoRepository<Session, String> {
}
