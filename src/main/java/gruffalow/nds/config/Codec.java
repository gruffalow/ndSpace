package gruffalow.nds.config;

import gruffalow.nds.codec.BasicNDSCodec;
import gruffalow.nds.codec.NDSCodec;
import java.util.function.Supplier;

public enum Codec {
    BASIC(BasicNDSCodec::new);

    private final Supplier<NDSCodec> codecSupplier;

    Codec(Supplier<NDSCodec> codecSupplier) {
        this.codecSupplier = codecSupplier;
    }

    public NDSCodec getCodecInstance() {
        return codecSupplier.get();
    }

}
