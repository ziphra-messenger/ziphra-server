package com.privacity.server.loadbalance;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



@RestController
@RequestMapping(path = "/entry")
public class MainController {
	
	
	@Autowired
	ForwardManagerService f;
	   

	Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
			.create();
	
	
	@PostMapping("/{controller}")
	public void main(@RequestBody Object request, @PathVariable String controller, HttpServletResponse response , @RequestHeader Map<String, String> headers) throws IOException {
		System.out.println("ob: " + request.toString());
	String destino = f.get() + "/entry/" + controller;
		System.out.println("DESTINO: " + destino);
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost request2 = new HttpPost(destino);
		  System.out.println("-1-------------------");
		for (int i = 0 ; i <  request2.getAllHeaders().length ; i++) {
		    System.out.println(request2.getAllHeaders()[i].getValue() + "/ ---- / " + request2.getAllHeaders()[i].getName());
		    
			}
		  System.out.println("-2-------------------");
		for (int i = 0 ; i <  request2.getAllHeaders().length ; i++) {
	    System.out.println(request2.getAllHeaders()[i].getValue() + "/ ---- / " + request2.getHeaders(request2.getAllHeaders()[i].getName()));
	    request2.removeHeaders(request2.getAllHeaders()[i].getName());
	    request2.removeHeaders("Content-Length");
	    
		}

		headers.remove("content-length");
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			request2.removeHeaders(entry.getKey());
			request2.removeHeaders(entry.getKey());
			request2.addHeader(entry.getKey(), entry.getValue());
			System.out.println(entry.getKey() + "/ headers/ " + entry.getValue());
	    //model.addAttribute(entry.getKey(), entry.getValue());
		}
		  System.out.println("-3-------------------");
		for (int i = 0 ; i <  request2.getAllHeaders().length ; i++) {
		    System.out.println(request2.getAllHeaders()[i].getValue() + "/ ---- / " + request2.getAllHeaders()[i].getName());
		    
			}
//		request2.getParams().setParameter("request", request);
//		request2.add

	    

	    final String json = gson.toJson(request);
	    final StringEntity entity = new StringEntity(json);

	    request2.setEntity(entity);
		
	    org.apache.http.HttpResponse response1 = client.execute(request2);
	    response.setContentType("application/json;charset=UTF-8");
	    ByteStreams.copy(response1.getEntity().getContent(), response.getOutputStream());
	}
}