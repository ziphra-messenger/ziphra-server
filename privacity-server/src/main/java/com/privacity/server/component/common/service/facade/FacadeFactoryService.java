package com.privacity.server.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.privacity.server.dao.factory.MessageIdSequenceFactory;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class FacadeFactoryService {
	@Autowired @Lazy
	private MessageIdSequenceFactory messageIdSequence;

	public MessageIdSequenceFactory messageIdSequence() {
		return messageIdSequence;
	}
	
}
