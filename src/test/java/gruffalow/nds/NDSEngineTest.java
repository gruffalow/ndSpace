package gruffalow.nds;

import static org.junit.Assert.assertEquals;

import gruffalow.nds.codec.BasicNDSCodec;
import gruffalow.nds.config.Codec;
import org.junit.Test;

public class NDSEngineTest {

    @Test
    public void defaultNDSEngineUsesBasicCodec() {
        NDSEngine engine = new NDSEngine();
        assertEquals(Codec.BASIC, engine.getConfig().getCodec());
        assertEquals(BasicNDSCodec.class, engine.getConfig().getCodec().getCodecInstance().getClass());
    }

    @Test
    public void codecIsConfiguredWithTheConfigFromTheEngine() {
        NDSEngine engine = new NDSEngine();
        assertEquals(engine.getConfig(), engine.getCodec().getConfig());
    }

}
