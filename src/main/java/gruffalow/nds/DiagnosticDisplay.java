package gruffalow.nds;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.concurrent.CountDownLatch;

public class DiagnosticDisplay extends Application {
    public static final CountDownLatch latch = new CountDownLatch(1);
    public static DiagnosticDisplay diagnosticDisplay = null;
    private ImageView imageView;

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

        imageView = new ImageView();
        //final Image image2 = new Image(DiagnosticDisplay.class.getResourceAsStream("/pexels-photo-149988.jpg"));

        final WritableImage image3 = new WritableImage(640,480);
        PixelWriter modelPixelWriter = image3.getPixelWriter();
        for (int x=0 ; x<640 ; x++) {
            for (int y=0; y<480 ; y++) {
                Color c = new Color(0.5, 0.5, 0.5, 0.5);
                modelPixelWriter.setColor(x, y, c);
            }
        }
        imageView.setImage(image3);

        

        final HBox pictureRegion = new HBox();

        pictureRegion.getChildren().add(imageView);
        gridpane.add(pictureRegion, 1, 1);


        root.getChildren().add(gridpane);
        primaryStage.setScene(scene);
        primaryStage.show();
        latch.countDown();
    }

    public void setModelImage(Image image) {
        imageView.setImage(image);
    }
}
