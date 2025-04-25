package com.privacity.core.idsprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privacity.core.interfaces.GetMaxIdInterface;
import com.privacity.core.model.Grupo;
import com.privacity.core.repository.GrupoRepository;

@Service
public class GrupoIdGeneratorService extends GenericSimpleIdGeneratorAbstractService {

	@Autowired
	private GrupoRepository r;
	@SuppressWarnings("rawtypes")
	@Override
	public GetMaxIdInterface getRepository() {
		return r;
	}
	protected Long idInitValue() {
		return Grupo.CONSTANT_ID_STARTS_AT;
	}
	
}
