package ar.ziphra.sessionmanager.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.ziphra.sessionmanager.model.Session;

public interface SessionRepository extends MongoRepository<Session, String> {
}
