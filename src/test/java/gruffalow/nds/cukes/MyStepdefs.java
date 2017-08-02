package gruffalow.nds.cukes;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import gruffalow.nds.AppMain;
import gruffalow.nds.NDSEngine;

import static org.junit.Assert.*;

public class MyStepdefs implements En{


    public MyStepdefs(MyStepdefsWorld world) {
        Given("^The NDS system is started$", () -> {
            AppMain appMain = new AppMain();
            appMain.runner(new String[0]);
            world.appmain = appMain;
        });

        Then("^the NDS Engine exists$", () -> {
            assertNotNull("NDS Engine should not be null after system start-up",world.appmain.engine);
        });

        And("^the NDS Engine is setup with the default codec$", () -> {
            NDSEngine defaultEngine = new NDSEngine();
            assertEquals(defaultEngine.getConfig().getCodec(), world.appmain.engine.getConfig().getCodec());
        });
    }


}
