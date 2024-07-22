package com.privacity.common.util;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class PersonalAesGenerator {
	
	
	static boolean show=true;
	static String mensaje;
    public PersonalAesGenerator() {
    }

    public static String a(String var0) {
        String var1 = "";
        int var2 = var0.toCharArray().length / 2;

        for(int var3 = 0; var3 < var2; ++var3) {
            try {
                var1 = var1 + var0.toCharArray()[var3];
                var1 = var1 + var0.toCharArray()[var2 + var3];
            } catch (Exception var5) {
                System.out.println("error");
            }
        }

        return var1;
    }

    public static List a(String var0, int var1) {
        ArrayList var2 = new ArrayList();
        int var3 = var0.length();

        for(int var4 = 0; var4 < var3; var4 += var1) {
            var2.add(var0.substring(var4, Math.min(var3, var4 + var1)));
        }

        return var2;
    }
    
    public static void time(int s) {
    	mensaje= "************ " + s+" ****  " + (LocalDateTime.now().format(DateTimeFormatter.ofPattern("mm:ss.SSS"))).toString();;
    	System.out.println(mensaje);
    }

    public static String[] main(String var0) {
        
    	int count=0;
    	String var1 = "79,102,98,113,-61,-109,-62,-65,125,33,119,-61,-119,112,-61,-102,120,-61,-111,-61,-95,116,89,101,64,-61,-87,60,74,69,85,81,51,100,75,52,105,62,78,117,55,35,-61,-83,86,58,-61,-79,49,53,111,118,65,67,54,104,99,90,83,121,95,106,82,76,57,107,84,108,114,88,115,50,45,-59,-110,48,66,103,56,-59,-95,68,71,-61,-67,97,87,122,80,59,110,-58,-110,109,70,72,73,37,77,126";
        String[] var2 = var1.split(",");
        time(count++);
        byte[] var3 = new byte[var2.length];
        var1 = "";
        time(count++);
        int var4;
        for(var4 = 0; var4 < var2.length; ++var4) {
            byte var5 = new Byte(var2[var4]);
            var3[var4] = var5;
        }
        time(count++);
        var1 = new String(var3);
        System.out.println(var0);
        time(count++);
        for(var4 = 0; var4 < var0.length(); ++var4) {
            var0 = a(var0);
        }
        time(count++);
        var0 = var0 + var0 + var1;

        for(var4 = 0; var4 < var0.length(); ++var4) {
            var0 = a(var0);
        }
        time(count++);
        System.out.println(var0);
        List var13 = a(var0, 2);
        byte[] var12 = new byte[var13.size()];
        var0 = "";
        time(count++);
        int var6;
        int var7;
        for(var6 = 0; var6 < var13.size(); ++var6) {
            var7 = new Byte(var12[var6]);
            var12[var6] = (byte)var7;
        }
        time(count++);
        System.out.println("-- > " + var0);
        var0 = Base64.getEncoder().withoutPadding().encodeToString(var12);
        time(count++);
        var0 = var0 + var1 + var1 + var1;
        time(count++);
        for(var6 = 0; var6 < var0.length(); ++var6) {
            var0 = a(var0);
        }
        time(count++);
        var0 = var0 + var0;
        var6 = var0.length() * 2;
        time(count++);
        System.out.println("-var0.length()- > " + var0.length());
        for(var7 = 0; var7 < var0.length()/20; ++var7) {
        	//time(count++);
            var0 = a(var0);
        }
        time(count++);
        System.out.println("-ssss- > " + var0);
        int var8 = var0.length() % 2 == 0 ? var0.length() / 2 : var0.length() / 2 + 1;
        time(count++);
        String var9 = var0.substring(0, var8);
        time(count++);
        String var10 = var0.substring(var8);
        time(count++);
        System.out.println("-iteration- > " + var6);
        time(count++);
        System.out.println("-key- > " + var9);
        time(count++);
        System.out.println("-salt- > " + var10);
        time(count++);
        String[] var11 = new String[]{"128", String.valueOf(var6), var9, var10};
        time(count++);
        return var11;
        
    }
//
//    public static void main(String[] var0) {
//
//        main("mel12124241241234123ina");
//    }
    
    
}