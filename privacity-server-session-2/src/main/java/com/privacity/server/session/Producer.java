package com.privacity.server.session;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import org.apache.commons.lang3.RandomStringUtils;

import com.privacity.server.common.util.AESToUse;

public class Producer implements Runnable {
    private static final Logger log = Logger.getLogger(Producer.class.getCanonicalName());
    private static final AtomicInteger idSequence = new AtomicInteger(0);
    private boolean running = false;
    private final DataQueue dataQueue;

    public Producer(DataQueue dataQueue) {
        this.dataQueue = dataQueue;
    }

    @Override
    public void run() {
        running = true;
        produce();
    }

    public void stop() {
        running = false;
    }

    public void produce() {

        while (running) {

            if (dataQueue.isFull()) {
                try {
                    dataQueue.waitIsNotFull();
                } catch (InterruptedException e) {
                    log.severe("Error while waiting to Produce messages.");
                    break;
                }
            }

            // avoid spurious wake-up
            if (!running) {
                break;
            }

            if (!dataQueue.isFull()) {
            	  log.info("productor " + Thread.currentThread().getId() + " generando aes " + dataQueue.getSize());

            	dataQueue.add(generateMessage());
        
            }
            
            log.info("productor " + Thread.currentThread().getId() + " Size of the queue is: " + dataQueue.getSize());
            //ThreadUtil.sleep(2000L);
            //Sleeping on random time to make it realistic
            
        }

        log.info("Producer Stopped");
    }

    private AESToUse generateMessage() {
    	String AesKey =  RandomStringUtils.randomAlphabetic (1);
		String AesSalt = "qq" ;
		int AesIteration = 1;
		System.out.println("AesKey " + AesKey);
		System.out.println("AesSalt " + AesSalt);
		System.out.println("AesIteration " +AesIteration);
		
		AESToUse message = null;
		while (message == null) {
        try {

			
					
					message  = new AESToUse(128, AesIteration, AesKey,AesSalt );
			System.out.println(new java.util.Date());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
        return message;
    }

}
