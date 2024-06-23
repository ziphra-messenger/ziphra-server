package com.privacity.server.encrypt.pool;

import java.util.ArrayList;
import java.util.List;

import com.privacity.common.enumeration.RandomGeneratorType;
import com.privacity.common.util.RandomGenerator;
import com.privacity.server.main.AESToUse;

import lombok.Getter;

public final class ClassSingleton {

    private static ClassSingleton INSTANCE;
    private String info = "Initial info class";
    
    private ClassSingleton() {        
    }
    
    @Getter
    List<AESToUse> list = new ArrayList<>();
    	
    public static ClassSingleton getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ClassSingleton();
        }
        
        return INSTANCE;
    }

    Callback a;
    public void callback(Callback a) {
    	this.a =a;
    }
    
	public AESToUse get() {
//		if ( list.size() == 0) {
//			String AesKey = RandomGenerator.generate(RandomGeneratorType.ALPHANUMERIC, 64, 64) ;
//			String AesSalt = RandomGenerator.generate(RandomGeneratorType.ALPHANUMERIC, 5, 100) ;
//			Integer AesIteration = Integer.parseInt(RandomGenerator.generate(RandomGeneratorType.NUMERIC, 3, 6) );
//        	
//            try {
//				
//						
//				return		new AESToUse(128, AesIteration, AesKey,AesSalt );
//				
//			} catch (Exception e) {
//
//				e.printStackTrace();
//			}
//		}
		AESToUse r = list.remove(0);
		System.out.println("singleton lista  " + ClassSingleton.getInstance().getList().size());
		a.action();
		return r;
	}

	public void put(AESToUse i) {
		this.list.add( i);
	}

    
    // getters and setters
}