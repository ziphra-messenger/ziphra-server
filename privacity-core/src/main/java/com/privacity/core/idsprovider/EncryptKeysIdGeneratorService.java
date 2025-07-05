package com.privacity.core.idsprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.core.interfaces.GetMaxIdInterface;
import com.privacity.core.model.EncryptKeys;
import com.privacity.core.repository.EncryptKeysRepository;
@Service
public class EncryptKeysIdGeneratorService extends GenericSimpleIdGeneratorAbstractService {

	@Autowired
	@Lazy
	private EncryptKeysRepository r;
	
	@Override
	protected Long idInitValue() {
		return EncryptKeys.CONSTANT_ID_STARTS_AT;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public GetMaxIdInterface getRepository() {
		return r;
	}

}
