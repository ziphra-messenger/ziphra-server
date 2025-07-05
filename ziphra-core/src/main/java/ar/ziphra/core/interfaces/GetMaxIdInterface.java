package ar.ziphra.core.interfaces;

import org.springframework.data.repository.CrudRepository;

import ar.ziphra.commonback.annotations.SpringIgnoreThisComponent;

@SpringIgnoreThisComponent
public interface GetMaxIdInterface<T, ID>  extends CrudRepository<T, ID>{
	
	Long getMaxId();

}
