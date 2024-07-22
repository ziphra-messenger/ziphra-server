package com.privacity.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ZipUtil {
//	public static void main(String[] args) throws Exception {
//        String string = "I am what I am hhhhhhhhhhhhhhhhhhhhhhhhhhhhh"
//                + "bjggujhhhhhhhhh"
//                + "rggggggggggggggggggggggggg"
//                + "esfffffffffffffffffffffffffffffff"
//                + "esffffffffffffffffffffffffffffffff"
//                + "esfekfgy enter code here`etd`enter code here wdd"
//                + "heljwidgutwdbwdq8d"
//                + "skdfgysrdsdnjsvfyekbdsgcu"
//                +"jbujsbjvugsduddbdj";
//
//       System.out.println("after compress:");
//        byte[] compressed = new ZipUtil().compress(string.getBytes());
//        System.out.println(compressed.toString());
//        System.out.println(compressed.length);
//        System.out.println("after decompress:");
//        String decomp = new String( new ZipUtil().decompress(compressed), "UTF-8");
//        System.out.println(decomp);
//        System.out.println(decomp.length());
//    }

	public byte[] compress(String data) throws IOException {
		return compress(data.getBytes());
	}

	public byte[] compress(byte[] data) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(bos);
		gzip.write(data);
		gzip.close();
		byte[] compressed = bos.toByteArray();
		bos.close();
		return compressed;
	}
	
	public byte[] decompress(String data) throws IOException {
		return compress(data.getBytes());
	}

	public byte[] decompress(byte[] compressed) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
		GZIPInputStream gis = new GZIPInputStream(bis);
		BufferedReader br = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line;
		while((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		gis.close();
		bis.close();
		return sb.toString().getBytes();
	}
}