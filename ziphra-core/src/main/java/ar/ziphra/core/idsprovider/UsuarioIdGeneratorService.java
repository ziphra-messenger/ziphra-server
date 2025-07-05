package ar.ziphra.core.idsprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.ziphra.core.interfaces.GetMaxIdInterface;
import ar.ziphra.core.model.Usuario;
import ar.ziphra.core.repository.UsuarioRepository;

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
