package com.privacity.common;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
public class Test2  {






    public static void main(String[]  voids) throws IOException {

        final boolean  logOn;

        logOn= true;







            RestTemplate restTemplate = new RestTemplateBuilder().build();


              

         
            	    	
                        String url = "http://192.168.0.176:8080" + "/free/arch/download";

                        HttpHeaders headers = new HttpHeaders();
                        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
                        HttpEntity<String> entity = new HttpEntity<>(headers);
                        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);
                        System.out.println ( new String ( response.getBody(), Charset.defaultCharset()));
          

   
    }


    public static ClientHttpRequestFactory httpRequestFactory() {
    	
    	HttpClient client = HttpClients.custom()
                        .build();
    	
        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(client);
        requestFactory.setBufferRequestBody(false);
        return requestFactory;
    }

 
    public static RestTemplate getRestTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(httpRequestFactory()); // apache http library
        restTemplate.setMessageConverters(getMessageConverters());
        return restTemplate;
    }


    private static List<HttpMessageConverter<?>> getMessageConverters() {
        final List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        final FormHttpMessageConverter e = new FormHttpMessageConverter();

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));

        //MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
        //converter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        //e.addPartConverter(jsonHttpMessageConverter);
        converters.add(e);
        converters.add(converter);
        return converters;
    }


    public static CommonsMultipartResolver multipartResolver() {
        final CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        return commonsMultipartResolver;
    }


  

}

