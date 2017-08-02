package gruffalow.nds.codec;

import java.io.IOException;
import java.io.InputStream;

public class LocalCopyNDSCodec extends TypicalBaseNDSCodec{

    @Override
    public void initialize() {

    }

    @Override
    public void seedKey(InputStream keyStream) {

    }

    @Override
    public void encode(InputStream rawInput) throws IOException {
        byte[] buffer = new byte[102400];
        int len = rawInput.read(buffer);
        while (len != -1) {
            getOutputStream().write(buffer, 0, len);
            len = rawInput.read(buffer);
        }
        rawInput.close();
        getOutputStream().close();
    }

    @Override
    public void decode(InputStream encodedInput) throws IOException {
        encode(encodedInput);
    }
}
