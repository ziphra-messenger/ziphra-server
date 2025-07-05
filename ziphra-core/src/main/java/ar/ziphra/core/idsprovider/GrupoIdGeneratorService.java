package ar.ziphra.core.idsprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.ziphra.core.interfaces.GetMaxIdInterface;
import ar.ziphra.core.model.Grupo;
import ar.ziphra.core.repository.GrupoRepository;

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
