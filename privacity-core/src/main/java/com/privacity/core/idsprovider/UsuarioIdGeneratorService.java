package com.privacity.core.idsprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privacity.core.interfaces.GetMaxIdInterface;
import com.privacity.core.model.Usuario;
import com.privacity.core.repository.UsuarioRepository;

@Service
public class UsuarioIdGeneratorService extends GenericSimpleIdGeneratorAbstractService {

	@Override
	protected Long idInitValue() {
		return Usuario.CONSTANT_ID_STARTS_AT;
	}
	@Autowired
	private UsuarioRepository r;
	@SuppressWarnings("rawtypes")
	@Override
	public GetMaxIdInterface getRepository() {
		return r;
	}

}
