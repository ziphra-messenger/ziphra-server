package ar.ziphra.sessionmanager.util.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class ProducersGenerator {
	
	  

	
    private static final int MAX_QUEUE_CAPACITY = 5;
    public static DataQueue dataQueue = new DataQueue(MAX_QUEUE_CAPACITY);
    private static final Logger log = Logger.getLogger(ProducersGenerator.class.getCanonicalName());
    
    @Value("${ar.ziphra.sessionmanager.util.pool.ProducersGenerator.keyMinLenght}")
    private Integer keyMinLenght;
    @Value("${ar.ziphra.sessionmanager.util.pool.ProducersGenerator.keyMaxLenght}")
    private Integer keyMaxLenght;
    @Value("${ar.ziphra.sessionmanager.util.pool.ProducersGenerator.saltMinLenght}")
    private Integer saltMinLenght;
    @Value("${ar.ziphra.sessionmanager.util.pool.ProducersGenerator.saltMaxLenght}")
    private Integer saltMaxLenght;
    @Value("${ar.ziphra.sessionmanager.util.pool.ProducersGenerator.iterationMinValue}")
    private Integer iterationMinValue;
    @Value("${ar.ziphra.sessionmanager.util.pool.ProducersGenerator.iterationMaxValue}")
    private Integer iterationMaxValue;
    @Value("${ar.ziphra.sessionmanager.util.pool.ProducersGenerator.bits}")
    private Integer bits;
    @Value("${ar.ziphra.sessionmanager.util.pool.ProducersGenerator.randomGeneratorType}")
    private String randomGeneratorType;
    
    @PostConstruct    
    public void ProducerConsumerDemonstrator() {
        
    	log.info("Only one Producer Configuration");
        Producer producer = new Producer( dataQueue,  keyMinLenght,  keyMaxLenght,  saltMinLenght,  saltMaxLenght,
   			 iterationMinValue,  iterationMaxValue,  bits,  randomGeneratorType);

        Thread producerThread = new Thread(producer);

       // Consumer consumer = new Consumer(dataQueue);
      //  Thread consumerThread = new Thread(consumer);

        producerThread.start();
        //consumerThread.start();

        List<Thread> threads = new ArrayList<>();
        threads.add(producerThread);
        //threads.add(consumerThread);

        // let threads run for two seconds
       // sleep(2000);

        // stop threads
        //producer.stop();
        //consumer.stop();

        //waitForAllThreadsToComplete(threads);
    }

    public void demoMultipleProducersAndMultipleConsumers() {
        dataQueue = new DataQueue(MAX_QUEUE_CAPACITY);
        int producerCount = 5;
        log.info("Multi Producer Configuration Max: " + producerCount);
        //int consumerCount = 5;
        List<Thread> threads = new ArrayList<>();
        List<Producer> producers = new ArrayList<>();
       // List<Consumer> consumers = new ArrayList<>();

        for(int i = 0; i < producerCount; i++) {
            Producer producer = new Producer( dataQueue,  keyMinLenght,  keyMaxLenght,  saltMinLenght,  saltMaxLenght,
        			 iterationMinValue,  iterationMaxValue,  bits,  randomGeneratorType);
            Thread producerThread = new Thread(producer);
            producerThread.start();
            threads.add(producerThread);
            producers.add(producer);
        }
/*
        for(int i = 0; i < consumerCount; i++) {
            Consumer consumer = new Consumer(dataQueue);
            Thread consumerThread = new Thread(consumer);
            consumerThread.start();
            threads.add(consumerThread);
            consumers.add(consumer);
        }
*/
        // let threads run for ten seconds
//        sleep(10000);

        // stop threads
      //  consumers.forEach(Consumer::stop);
      //  producers.forEach(Producer::stop);

        //waitForAllThreadsToComplete(threads);
    }

//    public static void main(String[] args) throws Exception {
//    	
//    	
//    	new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				demoMultipleProducersAndMultipleConsumers();		
//			}
//		}).start();
//    	; 
//        //demoSingleProducerAndSingleConsumer();
//        
//        
//        
//        //Thread.sleep(1000);
//        
//        while ( true) {
//        	//Thread.sleep(1000);
//        	AESToUse m = ProducerConsumerDemonstrator.dataQueue.poll();
//        	log.info("-------------> " + m.toString());
//        }
//    }
}
