package ar.ziphra.core.idsprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ar.ziphra.core.interfaces.GetMaxIdInterface;
import ar.ziphra.core.model.EncryptKeys;
import ar.ziphra.core.repository.EncryptKeysRepository;
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
