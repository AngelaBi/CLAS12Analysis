package org.funp;
//---- imports for GROOT library
//import org.jlab.groot.data.*;
//import org.jlab.groot.graphics.*;
//---- imports for PHYSICS library
import org.jlab.jnp.physics.*;
//import org.jlab.jnp.reader.*;

import java.util.stream.Stream;


/**
 * testing LorentzVector
 */

public class VectorsTest
{
    public static void main( String[] args )
    {
        Stream.of("a1", "a2", "a3").findFirst().ifPresent(System.out::println);
        LorentzVector v1=new LorentzVector();
        LorentzVector v2=new LorentzVector();
        System.out.println("ciao");
        System.out.println("ciao2");
        v1.setPxPyPzM(1, 0, 0, 0.5);
        v2.setPxPyPzM(1, 1, 0, 0.5);

        System.out.println("angle between v1 and v2" + v1.vect().angle(v2.vect()) );
    }
}
