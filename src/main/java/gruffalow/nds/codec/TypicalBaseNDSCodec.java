package gruffalow.nds.codec;

import gruffalow.nds.config.Config;

import java.io.OutputStream;

public abstract class TypicalBaseNDSCodec implements NDSCodec {

    private Config config;
    private OutputStream outputStream;

    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public NDSCodec withConfig(Config config) {
        this.setConfig(config);
        return this;
    }

    @Override
    public Config getConfig() {
        return config;
    }

    @Override
    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public NDSCodec withOutputStream(OutputStream outputStream) {
        setOutputStream(outputStream);
        return this;
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }
}
