package com.privacity.server.main;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.enumeration.ProtocoloComponentsEnum;
import com.privacity.server.common.enumeration.Urls;
import com.privacity.server.component.common.ControllerBase;
import com.privacity.server.component.encryptkeys.PrivacityRSAValidation;
import com.privacity.server.component.serverconf.ServerConfValidationService;



@RestController
@RequestMapping(path = "/free")
public class FreeAccessController extends ControllerBase{
	
	@SuppressWarnings("unused")
	private ServerConfValidationService serverConfValidationService;

	@SuppressWarnings("unused")
	private PrivacityRSAValidation privacityRSAValidation;
	
	public FreeAccessController(
			ServerConfValidationService serverConfValidationService,
			PrivacityRSAValidation privacityRSAValidation) throws Exception {
		
		this.serverConfValidationService = serverConfValidationService;
		this.privacityRSAValidation = privacityRSAValidation;

		
	
	}

	@PostMapping("/entry")
	public ProtocoloDTO in(@RequestBody ProtocoloDTO request) throws Exception {
//    	new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				ProducerConsumerDemonstrator.demoMultipleProducersAndMultipleConsumers();		
//			}
//		}).start();
//    	; 
//        //demoSingleProducerAndSingleConsumer();
//        
        
        
        //Thread.sleep(1000);
//        
//        while ( true) {
////        	//Thread.sleep(1000);
////        	AESToUse m = ProducerConsumerDemonstrator.dataQueue.poll();
////        	System.out.println("-------------> " + m.toString());
//        }


		return super.in(request);

	}



	
	@Override
	public boolean getEncryptIds() {
		return false;
	}

	@Override
	public boolean isSecure() {
		return false;
	}

	@Override
	public boolean isRequestId() {
		return false;
	}
	@Override
	public boolean showLog(ProtocoloDTO request) {
		return false;
	}	
	
	@Override
	public Urls getUrl() {
		return Urls.CONSTANT_URL_PATH_FREE;
	}	
}
