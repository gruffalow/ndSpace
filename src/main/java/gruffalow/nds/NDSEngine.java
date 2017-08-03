package gruffalow.nds;

import gruffalow.nds.codec.BitWritingException;
import gruffalow.nds.codec.NDSCodec;
import gruffalow.nds.config.Config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NDSEngine {
    private final Config config;

    public NDSEngine() {
        this(Config.defaultConfig());
    }

    public NDSEngine(Config config) {
        this.config = config;
    }

    public Config getConfig() {
        return config;
    }

    public NDSCodec getCodec() {
        return config.getCodec().getCodecInstance().withConfig(config);
    }

    public void encode(InputStream rawInput, OutputStream encodedOutput) throws IOException, BitWritingException {
        if (config.isVerbose()) System.out.println("Encoding using codec : "+config.getCodec());
        NDSCodec codec = getCodec().withOutputStream(encodedOutput);
        codec.initialize();
        codec.encode(rawInput);
        if (config.isVerbose()) System.out.println("Encoding complete");
    }

    public void decode(InputStream encodedInput, OutputStream rawOutput) throws IOException {
        if (config.isVerbose()) System.out.println("Decoding using codec : "+config.getCodec());
        NDSCodec codec = getCodec().withOutputStream(rawOutput);
        codec.initialize();
        codec.decode(encodedInput);
        if (config.isVerbose()) System.out.println("Decoding complete");
    }
}
