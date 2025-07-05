package ar.ziphra.server.tasks;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import ar.ziphra.server.component.common.service.facade.FacadeComponent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DatabasePhysicalDeleteTask extends Thread {


	@Autowired
	@Lazy
	private FacadeComponent comps;
	
	@Value("${usuario.sessioninfo.requestid.task.cleaner.time.miliseconds}")  
	private int sleepTime;	
	
    @PostConstruct
    public void postConstruct() {
       // this.start();
    }
  @Override
  public void run(){
	  sleepTime=5000;
	  log.debug("DatabasePhysicalDeleteTask thread start");
	  while (true) {

		  

		  log.trace("ejecutando DatabasePhysicalDeleteTask thread start");
//		  publicClean();
		  try {

			//  publicClean();
			  
			  Thread.sleep(sleepTime, 0);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	  }
  }



  
  private void publicClean() {
//	  	comps.repo().media().deleteLogicDelete();
//	  	comps.repo().messageDetailDeleted().deleteLogicDelete();
//	  	comps.repo().messageDetail().deleteLogicDeleteSinMensaje();
//	  	
//	  	comps.repo().message().deleteLogicDeleteParentReply();
//	  	comps.repo().message().deleteLogicDelete();
//	  	
//	  	comps.repo().grupoUserConf().deleteLogicDelete();
//	  	comps.repo().userForGrupo().deleteLogicDelete();

	  	
	  	
	}
}