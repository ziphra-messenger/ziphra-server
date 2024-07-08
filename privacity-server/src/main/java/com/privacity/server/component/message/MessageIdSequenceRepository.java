package com.privacity.server.component.message;
import org.springframework.data.repository.CrudRepository;

import com.privacity.server.model.MessageIdSequence;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface MessageIdSequenceRepository extends CrudRepository<MessageIdSequence, Long> {

}
