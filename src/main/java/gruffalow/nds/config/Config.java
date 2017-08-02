package gruffalow.nds.config;

public class Config {
    private Codec codec = Codec.BASIC;
    private boolean verbose = false;

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

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public Config withVerbose(boolean verbose) {
        setVerbose(verbose);
        return this;
    }
}
