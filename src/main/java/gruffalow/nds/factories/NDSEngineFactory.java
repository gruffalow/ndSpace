package gruffalow.nds.factories;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import gruffalow.nds.interfaces.INDSEngine;

/**
 * Created by pete on 25/06/2017.
 */
public class NDSEngineFactory {
    public static INDSEngine create() {
        IFn require = Clojure.var("clojure.core", "require");
        require.invoke(Clojure.read("gruffalow.nds.engine"));

        final IFn engineClass = Clojure.var("gruffalow.nds.engine", "ndsEngineClass");
        return (INDSEngine) engineClass.invoke();
    }
}
