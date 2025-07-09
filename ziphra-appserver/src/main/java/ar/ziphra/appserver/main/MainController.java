package ar.ziphra.appserver.main;

import java.awt.print.PrinterException;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import ar.ziphra.commonback.common.enumeration.ServerUrls;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/entry")
@Slf4j
public class MainController {
	
	

	@PostMapping("/{controller}")
	public ModelAndView in2(@PathVariable String controller, @RequestHeader Map<String, String> headers) throws PrinterException {
		

		
		log.debug("************* EN ENTRADA UNICA: controller: " + controller);
		
		log.trace("forward:"+Enum.valueOf(ServerUrls.class, controller));
		
		log.trace("Header count:" + headers.size());
		for (Map.Entry<String, String> entry : headers.entrySet()) {
		    log.trace("Header: " + entry.getKey() + " - value: " + entry.getValue());
		}
	        
			return new ModelAndView("forward:"+Enum.valueOf(ServerUrls.class, controller));

	}
}
