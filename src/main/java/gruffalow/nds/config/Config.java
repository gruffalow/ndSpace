package gruffalow.nds.config;

import gruffalow.nds.DiagnosticDisplay;

public class Config {
    private Codec codec = Codec.BASIC;
    private boolean verbose = false;
    private DiagnosticDisplay diagnosticDisplay;
    private boolean timings;

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

    public DiagnosticDisplay getDiagnosticDisplay() {return diagnosticDisplay;}

    public void setDiagnosticDisplay(DiagnosticDisplay diagnosticDisplay) {this.diagnosticDisplay = diagnosticDisplay;}

    public Config withDiagnosticDisplay(DiagnosticDisplay diagnosticDisplay) {
        setDiagnosticDisplay(diagnosticDisplay);
        return this;
    }

    public Config withTimings(boolean timings) {
        setTimings(timings);
        return this;
    }

    public void setTimings(boolean timings) {
        this.timings = timings;
    }

    public boolean isTimings() {
        return timings;
    }
}
