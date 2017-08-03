package gruffalow.nds;

import gruffalow.nds.codec.BitWritingException;
import gruffalow.nds.config.Codec;
import gruffalow.nds.config.Config;
import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.*;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppMain {
    private static final String CODEC = "codec";
    private static final String ENCODE = "encode";
    private static final String DECODE = "decode";
    private static final String FILE = "file";
    private static final String VERBOSE = "verbose";
    private static final String HELP = "help";
    private static final String QUESTION_MARK = "?";
    public NDSEngine engine;
    public DiagnosticDisplay diagnosticDisplay;

    public AppMain(DiagnosticDisplay diagnosticDisplay) {
        this.diagnosticDisplay = diagnosticDisplay;
    }

    public AppMain() {}

    public static void main(String[] args) {
        AppMain appMain = null;
        OptionParser parser = getOptionParser();
        OptionSet options = parser.parse(args);
        if (options.has(VERBOSE)) {
            Thread diagnosticDisplayThread = new Thread() {
                @Override
                public void run() {
                    javafx.application.Application.launch(DiagnosticDisplay.class);
                }
            };
            diagnosticDisplayThread.start();
            DiagnosticDisplay diagnosticDisplay = DiagnosticDisplay.waitForStartUpTest();
            appMain = new AppMain(diagnosticDisplay);
        } else {
            appMain = new AppMain();
        }

        try {
            appMain.runner(args);
        } catch (OptionException|IOException|IllegalArgumentException|BitWritingException ex) {
            System.err.println(ex.getMessage());
        }
    }



    public void runner(String[] args) throws IOException, BitWritingException {
        OptionParser parser = getOptionParser();
        OptionSet options = parser.parse(args);
        if (options.has(HELP)) {
            try {
                System.out.println("N-Dimensional Space Mapping provides a technique to compress and encrypt data within a single algorithm.");
                System.out.println("");
                parser.printHelpOn(System.out);
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
            return;
        }
        Config config = createConfig(options);
        engine = new NDSEngine(config);
        if (options.has(ENCODE)) {
            InputStream rawInputStream = getInputEncodingStream(options, config);
            OutputStream encodedOutputStream = getEncodedOutputStream(options, config);
            engine.encode(rawInputStream, encodedOutputStream);
        }
        if (options.has(DECODE)) {
            InputStream encodedStream = getInputDecodingStream(options, config);
            OutputStream decodedOutputStream = getDecodedOutputStream(options, config);
            engine.decode(encodedStream, decodedOutputStream);
        }
    }

    private FileOutputStream getEncodedOutputStream(OptionSet options, Config config) throws FileNotFoundException {
        File inputFile = (File)options.valueOf(FILE);
        File outputFile = new File(String.format("%s.nds", inputFile.getAbsolutePath()));
        if (config.isVerbose()) System.out.println("Output File : "+outputFile.getAbsolutePath());
        return new FileOutputStream(outputFile);
    }

    private FileOutputStream getDecodedOutputStream(OptionSet options, Config config) throws FileNotFoundException {
        File inputFile = (File)options.valueOf(FILE);
        String absolutePath = inputFile.getAbsolutePath();
        File outputFile = new File(absolutePath.substring(0,absolutePath.length()-4));
        if (config.isVerbose()) System.out.println("Output File : "+outputFile.getAbsolutePath());
        return new FileOutputStream(outputFile);
    }

    private FileInputStream getInputEncodingStream(OptionSet options, Config config) throws FileNotFoundException {
        File file =(File)options.valueOf(FILE);

        if (config.isVerbose()) System.out.println("Input File : "+file.getAbsolutePath());
        return new FileInputStream(file);
    }

    private FileInputStream getInputDecodingStream(OptionSet options, Config config) throws FileNotFoundException {
        File file =(File)options.valueOf(FILE);
        String absolutePath = file.getAbsolutePath();
        if (!absolutePath.substring(absolutePath.length()-4).equals(".nds"))
            throw new IllegalArgumentException(MessageFormat.format("{0} is not an nds encoded file", file.getName()));

        if (config.isVerbose()) System.out.println("Input File : "+file.getAbsolutePath());
        return new FileInputStream(file);
    }

    private Config createConfig(OptionSet options) {
        Config config = new Config().withCodec((Codec) options.valueOf(CODEC));
        if (options.has(VERBOSE)) config=config.withVerbose(true);
        return config;
    }

    private static OptionParser getOptionParser() {
        return new OptionParser(){
            {
                acceptsAll(Arrays.asList( HELP, QUESTION_MARK ), "show help" ).forHelp();
                accepts(VERBOSE, "verbose output");
                mutuallyExclusive(
                    accepts(ENCODE, "Encode the input file"),
                    accepts(DECODE, "Decode the input file")
                );
                accepts(FILE, "File to operate on").requiredIf(ENCODE, DECODE)
                        .withRequiredArg().ofType(File.class);
                accepts( CODEC ).withRequiredArg().ofType( Codec.class )
                        .describedAs(codecNames()).defaultsTo( Codec.BASIC );
            }

            String codecNames() {
                return Stream.of(Codec.values()).
                        map(Codec::name).
                        collect(Collectors.joining(", "));
            }
        };
    }
}
