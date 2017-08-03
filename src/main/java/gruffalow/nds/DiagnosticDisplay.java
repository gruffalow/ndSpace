package gruffalow.nds;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.concurrent.CountDownLatch;

public class DiagnosticDisplay extends Application {
    public static final CountDownLatch latch = new CountDownLatch(1);
    public static DiagnosticDisplay diagnosticDisplay = null;

    public static DiagnosticDisplay waitForStartUpTest() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return diagnosticDisplay;
    }

    public static void setDiagnosticDisplay(DiagnosticDisplay diagnosticDisplay0) {
        diagnosticDisplay = diagnosticDisplay0;
        latch.countDown();
    }

    public DiagnosticDisplay() {
        setDiagnosticDisplay(this);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("NDS Diagnostics");
        Group root = new Group();
        Scene scene = new Scene(root, 670, 510, Color.WHITE);

        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(10);
        gridpane.setVgap(10);

        final ImageView imv = new ImageView();
        final Image image2 = new Image(DiagnosticDisplay.class.getResourceAsStream("/pexels-photo-149988.jpg"));
        imv.setImage(image2);

        final HBox pictureRegion = new HBox();

        pictureRegion.getChildren().add(imv);
        gridpane.add(pictureRegion, 1, 1);


        root.getChildren().add(gridpane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
