package com.privacity.server.tasks;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.privacity.common.dto.RequestIdDTO;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.security.UsuarioSessionInfo;

import lombok.extern.java.Log;

@Component
@Log
public class DatabasePhysicalDeleteTask extends Thread {


	@Autowired
	@Lazy
	private FacadeComponent comps;
	

	
	@Value("${usuario.sessioninfo.requestid.task.cleaner.time.miliseconds}")  
	private int sleepTime;	
	
    @PostConstruct
    public void postConstruct() {
        this.start();
    }
  @Override
  public void run(){
	  sleepTime=5000;
	  System.out.println ("DatabasePhysicalDeleteTask thread start");
	  while (true) {

		  

		  //System.out.println ("ejecutando DatabasePhysicalDeleteTask thread start");
		  publicClean();
		  try {

			  publicClean();
			  
			  Thread.sleep(sleepTime, 0);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	  }
  }



  
  private void publicClean() {/*
	  	comps.repo().media().deleteLogicDelete();
	  	comps.repo().messageDetailDeleted().deleteLogicDelete();
	  	comps.repo().messageDetail().deleteLogicDeleteSinMensaje();
	  	
	  	comps.repo().message().deleteLogicDeleteParentReply();
	  	comps.repo().message().deleteLogicDelete();
	  	
	  	comps.repo().grupoUserConf().deleteLogicDelete();
	  	comps.repo().userForGrupo().deleteLogicDelete();

	  	*/
	  	
	}
}