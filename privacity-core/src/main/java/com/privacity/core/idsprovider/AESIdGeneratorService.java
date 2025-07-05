package com.privacity.core.idsprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.core.interfaces.GetMaxIdInterface;
import com.privacity.core.model.AES;
import com.privacity.core.repository.AESRepository;

@Service
public class AESIdGeneratorService extends GenericSimpleIdGeneratorAbstractService {

	@Autowired
	@Lazy
	private AESRepository r;
	
	@SuppressWarnings("rawtypes")
	@Override
	public GetMaxIdInterface getRepository() {
		return r;
	}
	
	protected Long idInitValue() {
		return AES.CONSTANT_ID_STARTS_AT;
	}
}
