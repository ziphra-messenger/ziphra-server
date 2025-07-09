package ar.ziphra.core.idsprovider;

import ar.ziphra.core.interfaces.GetMaxIdInterface;
import ar.ziphra.core.interfaces.SimpleIdIdsGeneratorInterface;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public abstract class GenericSimpleIdGeneratorAbstractService  implements SimpleIdIdsGeneratorInterface{
	
	private static final String CONSTANT_TAB = "\t";
	private Long lastId;
	private Long lastIdRepository=0L;
	
	@SuppressWarnings("rawtypes")
	public abstract GetMaxIdInterface getRepository();
	

	public void pc() {
		log.info("Instanciando: " + getRepositoryName());
		inicializandoId();
	}
	
	private void inicializandoId() {
		log.info(CONSTANT_TAB + "Getting maxId");
		lastId = getMaxIdFromRepository();
		if (lastId == null) {
			lastId=0L;
		}
		log.info(CONSTANT_TAB + "maxId: " + lastId);
	}
	
	private void refresh() {
		log.info("Refresing: " + getRepositoryName());
		inicializandoId();
	}

	private synchronized Long getMaxIdFromRepository() {
		Long r = getRepository().getMaxId();
		if (r==null || r< idInitValue())r=idInitValue();
		
		if (r <= lastIdRepository) r=lastIdRepository;
		return r;
	}
	
	public synchronized Long getNextIdFromRepository() {
		Long r = getRepository().getMaxId() + 1;
		lastIdRepository=r;
		return r;
	}
	
	public synchronized Long getNextId(boolean refresh) {
		if(refresh) { refresh();}
		if (lastId==null){ pc();}
		
		lastId++;
		log.debug(CONSTANT_TAB +getRepositoryName() + " Id Generado: " + lastId);
		return lastId;

	}
	public synchronized Long getNextId() {
		return getNextId(false);
	}

	protected String getRepositoryName() {
		return this.getClass().getSimpleName();
	}

	protected Long idInitValue() {
		return 1L;
	}

	
}