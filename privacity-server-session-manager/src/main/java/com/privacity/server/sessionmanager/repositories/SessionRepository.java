package com.privacity.server.sessionmanager.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.privacity.server.sessionmanager.model.Session;

public interface SessionRepository extends MongoRepository<Session, String> {
}
