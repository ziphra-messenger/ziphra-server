package com.privacity;


// Java program to demonstrate working of shuffle()
import java.util.*;
 
public class GFG {
    public static void main(String[] args)
    {
        ArrayList<String> mylist = new ArrayList<String>();
        mylist.add("1");
        mylist.add("2");
        mylist.add("3");
        mylist.add("4");
 
 
        System.out.println("Original List : \n" + mylist);
 
        Collections.shuffle(mylist);
        Collections.shuffle(mylist);
        Collections.shuffle(mylist);
        System.out.println("\nShuffled List : \n" + mylist);
    }
}