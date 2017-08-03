package gruffalow.nds.codec;


import gruffalow.nds.DiagnosticDisplay;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TreeSpaceNode {
    private TreeSpaceNode lower = null;
    private TreeSpaceNode higher = null;
    long startInclusive;
    long endInclusive;
    private int offsetBits;
    private List<TreeSpaceNode> contained = new ArrayList<>();

    public TreeSpaceNode(long startInclusive, long endInclusive) {
        this.startInclusive = startInclusive;
        this.endInclusive = endInclusive;
        offsetBits = Long.SIZE-Long.numberOfLeadingZeros(endInclusive-startInclusive);
    }

    public void writeAddress(long point, BitWriter writer) throws PathFindingException, IOException, BitWritingException {
        if (point<startInclusive || point>endInclusive)
            throw new PathFindingException(point+" not found in space "+startInclusive+"-"+endInclusive);
        if (contained.isEmpty()) {
            long offset=point-startInclusive;
            writer.writeLong(offsetBits, offset);
            return;
        }
        throw new PathFindingException("Splitting not yet implemented");     //Not done splitting yet
    }

    public void drawModel(DiagnosticDisplay diagnosticDisplay) {
        WritableImage wimage = new WritableImage(640,480);
        PixelWriter writer = wimage.getPixelWriter();
        PixelReader reader = wimage.getPixelReader();
        long ratio = (endInclusive-startInclusive)/639L;
        this.drawModel(writer, reader, 1, 640, 480, startInclusive, endInclusive, ratio);
        diagnosticDisplay.setModelImage(wimage);
    }

    private void drawModel(PixelWriter writer, PixelReader reader, int level, int width, int height, long numberSpaceStart, long numberSpaceEnd, long ratio) {

        int startPoint = Math.max((int) (startInclusive/ratio),0);
        int endPoint = Math.min((int) (endInclusive/ratio),width-1);
        double range = ((double)(endInclusive - startInclusive))/((double)(numberSpaceEnd - numberSpaceStart));
        for (int x = startPoint; x<=endPoint; x++) {
            Color c = new Color(1.0-range,range,1.0-range, 0.5);
                writer.setColor(x, level, c);
        }
        for (TreeSpaceNode child:contained) {
            child.drawModel(writer, reader, level+1, width, height, numberSpaceStart, numberSpaceEnd, ratio);
        }
    }
}
