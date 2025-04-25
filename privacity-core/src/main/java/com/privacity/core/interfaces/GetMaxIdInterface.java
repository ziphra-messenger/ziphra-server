package com.privacity.core.interfaces;

import org.springframework.data.repository.CrudRepository;

import com.privacity.commonback.annotations.SpringIgnoreThisComponent;

@SpringIgnoreThisComponent
public interface GetMaxIdInterface<T, ID>  extends CrudRepository<T, ID>{
	
	Long getMaxId();

}
