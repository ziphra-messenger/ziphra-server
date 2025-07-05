package ar.ziphra.core.idsprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ar.ziphra.core.interfaces.GetMaxIdInterface;
import ar.ziphra.core.model.AES;
import ar.ziphra.core.repository.AESRepository;

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
