package gruffalow.nds.codec;

import gruffalow.nds.config.Config;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

public class BasicNDSCodec extends TypicalBaseNDSCodec{

    TreeSpaceNode root = new TreeSpaceNode(0,0xffffffff);

    @Override
    public void initialize() {
        root = new TreeSpaceNode(0,0xffffffffL);

        if (getConfig().isVerbose())
            System.out.println(MessageFormat.format("root node from {0} to {1}", root.startInclusive, root.endInclusive));
    }

    @Override
    public void seedKey(InputStream keyStream) {

    }

    @Override
    public void encode(InputStream rawInput) throws IOException, BitWritingException {
        BitWriter writer = new BitWriter(getOutputStream());
        byte[] buffer = new byte[4];
        int len = rawInput.read(buffer);
        while (len != -1) {
            long point = 0;
            for (int i = 0; i < len; i++)
            {
                point = (point << 8) + (buffer[i] & 0xff);
            }
            if (len<buffer.length) {
                //todo: this is a problem, padding should not be done if we can't detect it any remove it on decode.
                point = (point << (8*(buffer.length-len)));
            }
            try {
                root.writeAddress(point, writer);
            } catch (PathFindingException e) {
                throw new IOException(e);
            }
            len = rawInput.read(buffer);
        }
        writer.flush();
        writer.close();
        rawInput.close();
    }

    @Override
    public void decode(InputStream encodedInput) {

    }
}
