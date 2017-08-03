package gruffalow.nds.cukes;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import gruffalow.nds.AppMain;
import gruffalow.nds.NDSEngine;
import gruffalow.nds.codec.BitWritingException;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

public class MyStepdefs implements En{


    public MyStepdefs(MyStepdefsWorld world) {
        Given("^The NDS system is started$", () -> {
            AppMain appMain = new AppMain();
            try {
                appMain.runner(new String[0]);
            } catch (IOException | BitWritingException e) {
                fail(e.getMessage());
            }
            world.appmain = appMain;
        });

        Then("^the NDS Engine exists$", () -> {
            assertNotNull("NDS Engine should not be null after system start-up",world.appmain.engine);
        });

        And("^the NDS Engine is setup with the default codec$", () -> {
            NDSEngine defaultEngine = new NDSEngine();
            assertEquals(defaultEngine.getConfig().getCodec(), world.appmain.engine.getConfig().getCodec());
        });

        Given("^The NDS system is started with the local copy codec$", () -> {
            world.inputFile = new File("src/test/resources/gruffalow/nds/cukes/localCopyCodec/input/jokes.txt");
            world.outputFile = new File("src/test/resources/gruffalow/nds/cukes/localCopyCodec/input/jokes.txt.nds");
            world.commandLine = "-v -cLOCALCOPY -encode -fsrc/test/resources/gruffalow/nds/cukes/localCopyCodec/input/jokes.txt".split(" ");
        });

        When("^a file is encoded$", () -> {
            AppMain appMain = new AppMain();
            try {
                appMain.runner(world.commandLine);
            } catch (IOException | BitWritingException e) {
                fail(e.getMessage());
            }
            world.appmain = appMain;
        });

        Then("^the output file is identical to the input file$", () -> {
            try {
                assertTrue("Input and Output Expected to be identical",IOUtils.contentEquals(new FileInputStream(world.inputFile), new FileInputStream(world.outputFile)));
                world.outputFile.delete();
            } catch (IOException e) {
                fail(e.getMessage());
            }
        });
    }


}
