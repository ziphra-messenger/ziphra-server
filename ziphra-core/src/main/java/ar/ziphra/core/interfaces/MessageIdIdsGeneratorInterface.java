package ar.ziphra.core.interfaces;

import ar.ziphra.common.exceptions.ZiphraException;

public interface MessageIdIdsGeneratorInterface {
	public Long getNextMessageId(Long idGrupo) throws ZiphraException;
	public Long getNextMessageIdFromRepository(Long idGrupo) throws ZiphraException;
}
