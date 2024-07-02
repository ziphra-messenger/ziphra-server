package com.privacity.server.component.messagedetail;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.privacity.server.common.model.MessageDetailDeleted;
import com.privacity.server.common.model.MessageDetailId;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface MessageDetailDeletedRepository extends CrudRepository<MessageDetailDeleted, MessageDetailId> {


	@Transactional
	@Modifying
	@Query("DELETE FROM  MessageDetailDeleted u where "
			+ "  u.deleted=true ")
	void deleteLogicDelete();
}
