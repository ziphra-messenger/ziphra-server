package com.privacity.server.loadbalance;


import java.time.LocalDateTime;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.privacity.common.dto.ProtocoloDTO;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping(path = "/entry2")
public class SendController {
	
	
	@Autowired
	ForwardManagerService f;
	   

	Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
			.create();
	
	@PostMapping("/CONSTANT_URL_PATH_PRIVATE_SEND")
	public ResponseEntity<String> inMessage(@RequestParam String request, 
			/*@RequestParam("data") */ MultipartFile data, @RequestHeader Map<String, String> headers2) 
	
	{
		 RestTemplate template = getRestTemplate();


         String url = "http://localhost:8080/private/send";
         final HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.MULTIPART_FORM_DATA);
         String authHeader = headers2.get( "authorization");
         headers.set( "Authorization", authHeader );



         MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
         parameters.set("Content-Type","multipart/form-data");
         parameters.set("Authorization-Type","multipart/form-data");


  

         if ( data != null){

             parameters.add("data", data);
         }else{
             parameters.add("data", "".getBytes());
         }
         parameters.add("request",request );
         
         final HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(
                 parameters, headers);
         
          ResponseEntity<String> s = template.exchange(url,
                 HttpMethod.POST, httpEntity, String.class);


         return s;

	}
	
    public  RestTemplate getRestTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        //restTemplate.setRequestFactory(httpRequestFactory()); // apache http library
        //restTemplate.setMessageConverters(getMessageConverters());
        return restTemplate;
    }

//	@PostMapping("/CONSTANT_URL_PATH_PRIVATE_SEND")
//	public void send(@RequestBody String request, 
//			/*@RequestParam("data") */ @RequestBody MultipartFile data, HttpServletResponse response , @RequestHeader Map<String, String> headers) throws IOException {
//		System.out.println("22222222222222222222222: " + request.toString());
//	String destino = f.get() + "/entry/" + "CONSTANT_URL_PATH_PRIVATE_SEND";
//		System.out.println("DESTINO: " + destino);
//		CloseableHttpClient client = HttpClients.createDefault();
//		HttpPost request2 = new HttpPost(destino);
//		  System.out.println("-1-------------------");
//		for (int i = 0 ; i <  request2.getAllHeaders().length ; i++) {
//		    System.out.println(request2.getAllHeaders()[i].getValue() + "/ ---- / " + request2.getAllHeaders()[i].getName());
//		    
//			}
//		  System.out.println("-2-------------------");
//		for (int i = 0 ; i <  request2.getAllHeaders().length ; i++) {
//	    System.out.println(request2.getAllHeaders()[i].getValue() + "/ ---- / " + request2.getHeaders(request2.getAllHeaders()[i].getName()));
//	    request2.removeHeaders(request2.getAllHeaders()[i].getName());
//	    request2.removeHeaders("Content-Length");
//	    
//		}
//
//		headers.remove("content-length");
////		headers.remove("Content-Type");
////		headers.remove("content-type");
//		for (Map.Entry<String, String> entry : headers.entrySet()) {
//			request2.removeHeaders(entry.getKey());
//			request2.removeHeaders(entry.getKey());
//			request2.addHeader(entry.getKey(), entry.getValue());
//			System.out.println(entry.getKey() + "/ headers/ " + entry.getValue());
//	    //model.addAttribute(entry.getKey(), entry.getValue());
//		}
//		  System.out.println("-3-------------------");
//		for (int i = 0 ; i <  request2.getAllHeaders().length ; i++) {
//		    System.out.println(request2.getAllHeaders()[i].getValue() + "/ ---- / " + request2.getAllHeaders()[i].getName());
//		    
//			}
////		request2.getParams().setParameter("request", request);
////		request2.add
//
//		//request2.setHeader  ("Content-Type","multipart/form-data");
//
//		final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//	    builder.addTextBody("request", request);
//	    
//	    if (data!=null) {
//	    builder.addBinaryBody(
//	        "data", data.getInputStream());
//	    }else {
//		    builder.addBinaryBody(
//			        "data", "".getBytes());
//
//	    }
//	    final HttpEntity multipart = builder.build();
//	    request2.setEntity(multipart);
//		
//	    org.apache.http.HttpResponse response1 = client.execute(request2);
//	    //response.setContentType("multipart/form-data");
//	    //response1.setHeader  ("Content-Type","multipart/form-data");
//	    ByteStreams.copy(response1.getEntity().getContent(), response.getOutputStream());
//	}
//}
}