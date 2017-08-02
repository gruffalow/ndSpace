package gruffalow.nds.codec;

import gruffalow.nds.config.Config;

import java.io.InputStream;
import java.io.OutputStream;

public interface NDSCodec {
    void setConfig(Config config);
    NDSCodec withConfig(Config config);
    Config getConfig();
    void initialize();
    void setOutputStream(OutputStream outputStream);
    OutputStream getOutputStream();
    void seedKey(InputStream keyStream);
    void encode(InputStream rawInput);
    void decode(InputStream encodedInput);
}
