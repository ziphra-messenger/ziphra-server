package ar.ziphra.core.interfaces;

import ar.ziphra.common.exceptions.ZiphraException;

public interface SimpleIdIdsGeneratorInterface {
	public Long getNextId() throws ZiphraException;
	public Long getNextIdFromRepository() throws ZiphraException;
}
