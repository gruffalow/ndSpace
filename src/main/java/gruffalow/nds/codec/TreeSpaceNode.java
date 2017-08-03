package gruffalow.nds.codec;


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

}
