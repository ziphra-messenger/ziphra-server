package ar.ziphra.appserver.main;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ziphra.common.dto.ProtocoloDTO;
import ar.ziphra.commonback.common.enumeration.ServerUrls;
import ar.ziphra.appserver.component.common.ControllerBase;
import ar.ziphra.appserver.component.encryptkeys.ZiphraRSAValidation;
import ar.ziphra.appserver.component.serverconf.ServerConfValidationService;



@RestController
@RequestMapping(path = "/free")
public class FreeAccessController extends ControllerBase{
	
	@SuppressWarnings("unused")
	private ServerConfValidationService serverConfValidationService;

	@SuppressWarnings("unused")
	private ZiphraRSAValidation ziphraRSAValidation;
	
	public FreeAccessController(
			ServerConfValidationService serverConfValidationService,
			ZiphraRSAValidation ziphraRSAValidation) throws Exception {
		
		this.serverConfValidationService = serverConfValidationService;
		this.ziphraRSAValidation = ziphraRSAValidation;

		
	
	}

	@PostMapping("/entry")
	public ProtocoloDTO in(@RequestBody ProtocoloDTO request)  {
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
	public boolean isSecure() {
		return false;
	}

	@Override
	public boolean isRequestId() {
		return false;
	}

	@Override
	public ServerUrls getUrl() {
		return ServerUrls.CONSTANT_URL_PATH_FREE;
	}	
}
