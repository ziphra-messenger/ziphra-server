package com.privacity.sessionmanager.util.pool;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

import com.privacity.sessionmanager.model.AESToUse;

import lombok.Getter;

public class DataQueue {
	
	private static final Logger Log = Logger.getLogger(DataQueue.class.getCanonicalName());
	
	@Getter
    private static final Queue<AESToUse> queue = new LinkedList<>();
    private final int maxSize;
    private final Object IS_NOT_FULL = new Object();
    private final Object IS_NOT_EMPTY = new Object();

    DataQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public synchronized boolean isFull() {
        return !(queue.size() < maxSize);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void waitIsNotFull() throws InterruptedException {
        synchronized (IS_NOT_FULL) {
            IS_NOT_FULL.wait();
        }
    }

    public void waitIsNotEmpty() throws InterruptedException {
        synchronized (IS_NOT_EMPTY) {
            IS_NOT_EMPTY.wait();
        }
    }

    public synchronized void add(AESToUse message) {
    	if ( isFull()) {
    		Log.finest("Queue Aes Full. Size: " + getSize());
    		return;
    	}
    	Log.finest("Aes Aceptada. Size: " + getSize());
        queue.add(message);
        notifyIsNotEmpty();
    }

    public AESToUse poll() {
    	
    	while (isEmpty()) {
    	if (isEmpty()) {
    		Log.finest("Esperando 2");
    		 notifyIsNotFull();
    		 try {
    			 Log.finest("Esperando 1");
    			 //waitIsNotFull();
    			ThreadUtil.sleep(500);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	}
    	AESToUse mess = queue.poll();
        notifyIsNotFull();
        return mess;
    }

    public Integer getSize() {
        return queue.size();
    }

    private void notifyIsNotFull() {
        synchronized (IS_NOT_FULL) {
            IS_NOT_FULL.notify();
        }
    }

    public void notifyIsNotEmpty() {
        synchronized (IS_NOT_EMPTY) {
            IS_NOT_EMPTY.notify();
        }
    }
}
