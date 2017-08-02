package gruffalow.nds;

import gruffalow.nds.codec.NDSCodec;
import gruffalow.nds.config.Config;

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
}
