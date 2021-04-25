package org.funp.utilities;
import java.util.*;
import java.io.*;
public class runUtil {
    public static HashMap<Integer, Double> createrunmap(){
        HashMap<Integer, Double> hmap = new HashMap<Integer, Double>();
        Double beam10p6=10.5986;
        Double beam10p2=10.1998;
        Double beam10p4=10.4096;
          //5nA runs
        hmap.put(6226,beam10p6);
        hmap.put(6322,beam10p6);
        hmap.put(6323,beam10p6);
        hmap.put(6371,beam10p6);
        hmap.put(6373,beam10p6);
        hmap.put(6374,beam10p6);
        hmap.put(6446,beam10p2);
        hmap.put(6447,beam10p2);
        hmap.put(6448,beam10p2);
        //regular runs
        hmap.put(6302,beam10p6);//check
        hmap.put(6303,beam10p6);
        hmap.put(6305,beam10p6);
        hmap.put(6307,beam10p6);
        hmap.put(6310,beam10p6);
        hmap.put(6311,beam10p6);
        hmap.put(6313,beam10p6);
        hmap.put(6321,beam10p6);
        hmap.put(6326,beam10p6);
        hmap.put(6327,beam10p6);
        hmap.put(6328,beam10p6);
        hmap.put(6346,beam10p6);
        hmap.put(6347,beam10p6);
        hmap.put(6349,beam10p6);

        hmap.put(6420,beam10p2);
        hmap.put(6428,beam10p2);
        hmap.put(6433,beam10p2);
        hmap.put(6442,beam10p2);
        hmap.put(6450,beam10p2);
        hmap.put(6467,beam10p2);
        hmap.put(6474,beam10p2);
        hmap.put(6481,beam10p2);
        hmap.put(6492,beam10p2);
        hmap.put(6501,beam10p2);
        hmap.put(6515,beam10p2);
        hmap.put(6522,beam10p2);
        hmap.put(6524,beam10p2);
        hmap.put(6546,beam10p2);
        hmap.put(6559,beam10p2);
        hmap.put(6571,beam10p2);
        hmap.put(6586,beam10p2);
        hmap.put(6595,beam10p2);
        hmap.put(11159,beam10p4);

        return hmap;
        
        
        
        // for (int i=11093; i<=11283;i++){
        //   hmap.put(i,beam10p4);
        // }
        // hmap.put(6302,beam10p6);
        // hmap.put(6303,beam10p6);
        // hmap.put(6305,beam10p6);
        // hmap.put(6307,beam10p6);
        // hmap.put(6310,beam10p6);
        // hmap.put(6313,beam10p6);
        // hmap.put(6321,beam10p6);
        // hmap.put(6311,beam10p6);
        // hmap.put(6327,beam10p6);
        // hmap.put(6346,beam10p6);
        // hmap.put(6347,beam10p6);
        // hmap.put(6349,beam10p6);
    
        // hmap.put(6428,beam10p2);
        // hmap.put(6433,beam10p2);
        // hmap.put(6442,beam10p2);
        // hmap.put(6450,beam10p2);
        // hmap.put(6467,beam10p2);
        // hmap.put(6474,beam10p2);
        // hmap.put(6481,beam10p2);
        // hmap.put(6492,beam10p2);
        // return hmap;
    
    }
}
