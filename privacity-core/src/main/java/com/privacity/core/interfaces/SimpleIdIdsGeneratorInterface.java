package com.privacity.core.interfaces;

import com.privacity.common.exceptions.PrivacityException;

public interface SimpleIdIdsGeneratorInterface {
	public Long getNextId() throws PrivacityException;
	public Long getNextIdFromRepository() throws PrivacityException;
}
