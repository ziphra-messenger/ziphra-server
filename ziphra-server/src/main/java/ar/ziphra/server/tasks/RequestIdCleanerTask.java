package ar.ziphra.server.tasks;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import ar.ziphra.server.component.common.service.facade.FacadeComponent;

import lombok.extern.java.Log;

@Component
@Log
public class RequestIdCleanerTask extends Thread {


	@Autowired
	@Lazy
	private FacadeComponent comps;
	
	@Value("${usuario.sessioninfo.requestid.expired.seconds}")
	private int expiredSeconds;
	
	@Value("${usuario.sessioninfo.requestid.task.cleaner.time.miliseconds}")  
	private int sleepTime;	
	
    @PostConstruct
    public void postConstruct() {
        this.start();
    }
  @Override
  public void run(){
	  System.out.println ("Request id thread start");
	  while (true) {
		  LocalDateTime date = LocalDateTime.now();
		  date = date.minusSeconds(expiredSeconds);
		  
		  publicClean(date);
		  privateClean(date);
		  ////System.out.println ("ejecutando Request id thread start");
		  log.fine("ejecutando public thread");
		  try {
			//Thread.sleep(sleepTime, 0);
			  Thread.sleep(expiredSeconds*1000, 0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
  }


  private void publicClean(LocalDateTime date) {
//		ConcurrentMap<String, RequestIdDTO> userMap = comps.service().requestIdPublic().getRequestIdsPublic();
//		  
//		  for (String userKey : userMap.keySet()) {
//			  
//			  if ( userMap.get(userKey).getDate().isBefore(date)) {
//				  log.fine("eliminando public " + userKey);
//				  userMap.remove(userKey);
//			  }
//		}
	}
  
	private void privateClean(LocalDateTime date) {
//		ConcurrentMap<String, UsuarioSessionInfo> map = comps.service().usuarioSessionInfo().getAll();
//		  
//		  for (String key : map.keySet()) {
//			    
//			  
//			  ConcurrentMap<String, RequestIdDTO> userMap = map.get(key).getRequestIds();
//			  
//			  for (String userKey : userMap.keySet()) {
//				  
//				  if ( userMap.get(userKey).getDate().isBefore(date)) {
//					  log.fine("eliminando " + userKey);
//					  userMap.remove(userKey);
//				  }
//			  }
//			  
//			}
	}
}