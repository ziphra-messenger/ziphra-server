package ar.ziphra.appserver.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Component
@NoArgsConstructor
@Accessors(fluent = true, chain = false)
@Getter
public class FacadeFactoryService {
	@Autowired @Lazy
	private ar.ziphra.appserver.factory.IdsGeneratorFactory idsGenerator;

}
