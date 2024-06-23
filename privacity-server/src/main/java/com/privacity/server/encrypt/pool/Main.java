package com.privacity.server.encrypt.pool;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import com.privacity.common.enumeration.RandomGeneratorType;
import com.privacity.common.util.RandomGenerator;
import com.privacity.server.main.AESToUse;
//@Service


class ProducerConsumer {
  //  @PostConstruct
    public void postConstruct() {

        ProducerConsumer producerConsumer = new ProducerConsumer();
        producerConsumer.producer();
        producerConsumer.consumer();
	}
	
    List<AESToUse> list = new ArrayList<>();
    Integer value = 0;

    private static final Integer MAX_SIZE = 10;
    private static final Object lock = new Object();

    /**
     * Creates a thread which produces integer numbers and fill them out into the
     * given list.
     */
    void producer() {

        Thread producerThread = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (list.size() >= MAX_SIZE) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    while (list.size() < MAX_SIZE) {
                    	System.out.println("add " + ClassSingleton.getInstance().getList().size());
                    	System.out.println("add2 " +list.size());
            			String AesKey =  RandomStringUtils.randomAlphabetic (1);
            			String AesSalt = RandomGenerator.generate(RandomGeneratorType.ALPHANUMERIC, 2000, 10000) ;
            			Integer AesIteration = Integer.parseInt(RandomGenerator.generate(RandomGeneratorType.NUMERIC, 3, 8) );
            			System.out.println("AesKey " + AesKey);
            			System.out.println("AesSalt " + AesSalt);
            			System.out.println("AesIteration " +AesIteration);
                        try {
                        	System.out.println(new java.util.Date());
							list.add(
									
									new AESToUse(512, AesIteration, AesKey,AesSalt )
									);
							System.out.println(new java.util.Date());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }
                    lock.notify();

                }
            }
        });

        producerThread.start();
    }

    /**
     * Creates a thread which consumes all the values produced by the producer.
     */
    void consumer() {

         Thread consumerThread = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                	ClassSingleton.getInstance().callback(new Callback() {
						
						@Override
						public synchronized  void action() {
						try {
							lock.notify();
				        } catch (Exception e1) {
				            //String msg = "InterruptedException: [" + e1.getLocalizedMessage() + "]";
				            //System.out.println (msg);
				            //e1.printStackTrace();
				            //System.out.flush();
				        }
							
				        System.out.println ("Worker done waiting, we're now waiting for it by joining");
				        try {
				        	lock.notifyAll();
				        } catch (Exception e1) { 
				            //String msg = "222: [" + e1.getLocalizedMessage() + "]";
				            //System.out.println (msg);
				            //e1.printStackTrace();
				            //System.out.flush();
				        }
						}
					});
                	
                    if ( list.size() == 0 || ClassSingleton.getInstance().getList().size() >= MAX_SIZE) {
                    	
                        while (list.size()+1 <= MAX_SIZE) {
                            try {
                            	
                                lock.wait();
                            } catch (InterruptedException e) {
                              //  e.printStackTrace();
                            }
                        }
                    }else {
                    	System.out.println("List is consumped " + ClassSingleton.getInstance().getList().size());
                    	ClassSingleton.getInstance().put(list.remove(0));	
                    	lock.notify();	
                    }
                    
                    




                    
                    
                }

            }

        });
        consumerThread.start();
    }
}

public class Main {
    public static void main(String[] args) {
        ProducerConsumer producerConsumer = new ProducerConsumer();
        producerConsumer.producer();
        producerConsumer.consumer();
        try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        while (true) {
        	try {
        	
        		String i = ClassSingleton.getInstance().get().getAES("hy644444444445g45glk3m45óigj4530+gjk4+hgk234'h5kg34+po5hkjw+p5rkghproetjhw+p4hjrtphojola");
        		System.out.println("--->" + i);
				Thread.sleep(10000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
        }
    }
}