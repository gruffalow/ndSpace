package gruffalow.nds;

import gruffalow.nds.factories.NDSEngineFactory;
import gruffalow.nds.interfaces.INDSEngine;

/**
 * Created by pete on 25/06/2017.
 */
public class AppMain {
    public static void main(String[] args) {
        System.out.println("N-Dimensional Space Mapping provides a technique to compress and encrypt data within a single algorithm.");
        INDSEngine engine = NDSEngineFactory.create();
        engine.initialize();
    }
}
