package com.privacity.server.session;

import java.util.LinkedList;
import java.util.Queue;

import com.privacity.server.common.util.AESToUse;

import lombok.Getter;

public class DataQueue {
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
    		System.out.println("cola aes rechazado " + getSize());
    		return;
    	}
    	System.out.println("aes aceptadp  " + getSize());
        queue.add(message);
        notifyIsNotEmpty();
    }

    public AESToUse poll() {
    	
    	while (isEmpty()) {
    	if (isEmpty()) {
    		 System.out.println("Esperando 2");
    		 notifyIsNotFull();
    		 try {
    			 System.out.println("Esperando 1");
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
