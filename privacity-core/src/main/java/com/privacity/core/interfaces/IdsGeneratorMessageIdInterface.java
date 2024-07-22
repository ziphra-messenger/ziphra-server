package com.privacity.core.interfaces;
import com.privacity.common.exceptions.PrivacityException;

public interface IdsGeneratorMessageIdInterface {
	
	Long getNextMessageId(Long idGrupo) throws PrivacityException;

}
