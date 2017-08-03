package gruffalow.nds.codec;

import gruffalow.nds.config.Config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface NDSCodec {
    void setConfig(Config config);
    NDSCodec withConfig(Config config);
    Config getConfig();
    void setOutputStream(OutputStream outputStream);
    NDSCodec withOutputStream(OutputStream outputStream);
    OutputStream getOutputStream();

    void initialize();
    void seedKey(InputStream keyStream);
    void encode(InputStream rawInput) throws IOException, BitWritingException;
    void decode(InputStream encodedInput) throws IOException;
}
