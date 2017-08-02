package gruffalow.nds.config;

public class Config {
    private Codec codec = Codec.BASIC;

    public static Config defaultConfig() {
        return new Config();
    }

    public void setCodec(Codec codec){
        this.codec = codec;
    }

    public Codec getCodec() {
        return codec;
    }

    public Config withCodec(Codec codec) {
        this.setCodec(codec);
        return this;
    }
}
