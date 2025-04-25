package com.privacity.core.interfaces;

import com.privacity.common.exceptions.PrivacityException;

public interface MessageIdIdsGeneratorInterface {
	public Long getNextMessageId(Long idGrupo) throws PrivacityException;
	public Long getNextMessageIdFromRepository(Long idGrupo) throws PrivacityException;
}
