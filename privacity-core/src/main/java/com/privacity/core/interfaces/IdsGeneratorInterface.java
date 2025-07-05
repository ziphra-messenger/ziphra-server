package com.privacity.core.interfaces;
import java.util.Map;

import com.privacity.common.exceptions.PrivacityException;

public interface IdsGeneratorInterface {
	
	Long getNextMessageId(Long idGrupo) throws PrivacityException;
	Long getNextGrupoId() throws PrivacityException;
	Long getNextUsuarioId() throws PrivacityException;
	Long getNextAESId() throws PrivacityException;
	Long getNextEncryptKeysId() throws PrivacityException;
	Map<Long,String> getRandomNicknameByGrupo(String idGrupo) throws PrivacityException;
	void refresh() throws PrivacityException;
}
