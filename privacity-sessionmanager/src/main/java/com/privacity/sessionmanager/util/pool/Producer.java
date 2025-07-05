package com.privacity.sessionmanager.util.pool;

import java.util.concurrent.ThreadLocalRandom;

import com.privacity.common.dto.AESDTO;
import com.privacity.common.enumeration.RandomGeneratorType;
import com.privacity.common.util.RandomGenerator;
import com.privacity.commonback.common.utils.AESToUse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Producer implements Runnable {

    private boolean running = false;
    private final DataQueue dataQueue;

    private final int keyMinLenght;
    private final int keyMaxLenght;
    private final int saltMinLenght;
    private final int saltMaxLenght;
    private final int iterationMinValue;
    private final int iterationMaxValue;
    private final int bits;
    private final String randomGeneratorType;
    		


    public Producer(DataQueue dataQueue, int keyMinLenght, int keyMaxLenght, int saltMinLenght, int saltMaxLenght,
			int iterationMinValue, int iterationMaxValue, int bits, String randomGeneratorType) {
		super();
		this.dataQueue = dataQueue;
		this.keyMinLenght = keyMinLenght;
		this.keyMaxLenght = keyMaxLenght;
		this.saltMinLenght = saltMinLenght;
		this.saltMaxLenght = saltMaxLenght;
		this.iterationMinValue = iterationMinValue;
		this.iterationMaxValue = iterationMaxValue;
		this.bits = bits;
		this.randomGeneratorType = randomGeneratorType;
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
                   // log.severe("Error while waiting to Produce messages.");
                    break;
                }
            }

            // avoid spurious wake-up
            if (!running) {
                break;
            }

            if (!dataQueue.isFull()) {
            	 // log.info("productor " + Thread.currentThread().getId() + " generando aes " + dataQueue.getSize());
            	
            	AESToUse m = generateMessage();
            	
            	dataQueue.add(m);
            	log.trace("Aes Generado: " + m.getAESDTO().toStringComplete() + " Queue Size: " + dataQueue.getSize() );
        
            }
            
            //log.info("productor " + Thread.currentThread().getId() + " Size of the queue is: " + dataQueue.getSize());
            //ThreadUtil.sleep(2000L);
            //Sleeping on random time to make it realistic
            
        }

        //log.info("Producer Stopped");
    }

    private AESToUse generateMessage() {
    	String AesKey =  RandomGenerator.generate(RandomGeneratorType.valueOf(randomGeneratorType), keyMinLenght, keyMaxLenght) ;
		String AesSalt = RandomGenerator.generate(RandomGeneratorType.valueOf(randomGeneratorType), saltMinLenght, saltMaxLenght) ;
		int AesIteration = ThreadLocalRandom.current().nextInt(iterationMinValue, iterationMaxValue + 1);
		
		AESToUse message = null;
		while (message == null) {
        try {
					
					message  = new AESToUse((new AESDTO()) 
							.setBitsEncrypt(bits+"")
							.setIteration(AesIteration+"")
							.setSecretKeyAES(AesKey)
							.setSaltAES(AesSalt)); 
							

					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
        return message;
    }

}
