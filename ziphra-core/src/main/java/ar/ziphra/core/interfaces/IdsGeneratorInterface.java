package ar.ziphra.core.interfaces;
import java.util.Map;

import ar.ziphra.common.exceptions.ZiphraException;

public interface IdsGeneratorInterface {
	
	Long getNextMessageId(Long idGrupo) throws ZiphraException;
	Long getNextGrupoId() throws ZiphraException;
	Long getNextUsuarioId() throws ZiphraException;
	Long getNextAESId() throws ZiphraException;
	Long getNextEncryptKeysId() throws ZiphraException;
	Map<Long,String> getRandomNicknameByGrupo(String idGrupo) throws ZiphraException;
	void refresh() throws ZiphraException;
}
