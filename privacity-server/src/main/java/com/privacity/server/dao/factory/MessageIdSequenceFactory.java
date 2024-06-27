package com.privacity.server.dao.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privacity.server.dao.impl.MessageIdLocal;
import com.privacity.server.dao.interfaces.MessgeIdInterfaceDAO;

@Service
public class MessageIdSequenceFactory {
	
	@Autowired
	private MessageIdLocal messageIdLocal;
	
	public MessgeIdInterfaceDAO getMessgeIdInterfaceDAO() {
		return messageIdLocal;
	}
	

}
