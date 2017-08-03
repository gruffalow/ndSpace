package gruffalow.nds.codec;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class BitWriter implements Closeable, Flushable{
    protected static int BIT_MASK[] =
            {0x80, 0x40, 0x20, 0x10, 0x08, 0x04, 0x02, 0x01};
    private final static byte ZERO = 0;

    private final OutputStream outputStream;
    private final int maxBufferBytes = 1024;
    private final int autoWriteThreshhold;
    private final int maxBitsPerWrite;

    private final byte[] bitBuffer;
    private int currentByte;
    private int bitsUsedinCurrentByte;

    public BitWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
        bitBuffer = new byte[maxBufferBytes];
        Arrays.fill(bitBuffer,0,bitBuffer.length-1,ZERO);
        autoWriteThreshhold = maxBufferBytes/2;
        maxBitsPerWrite=(maxBufferBytes-autoWriteThreshhold-1)*8;
        currentByte = 0;
        bitsUsedinCurrentByte = 0;
    }

    public void writeLong(int lowestNBits, long value) throws IOException, BitWritingException {

        final boolean[] bits = new boolean[64];
        for (int i = 0; i < 64; i++) {
            bits[64 - 1 - i] = (1L << i & value) != 0;
        }

        writeBits(lowestNBits, bits);
    }

    private void writeBits(int lowestNBits, boolean[] bits) throws BitWritingException, IOException {
        if (lowestNBits>maxBitsPerWrite)
            throw new BitWritingException("Buffers too small to write "+lowestNBits+" bits in one operation, reduce to "+maxBitsPerWrite);

        for (int position=bits.length-lowestNBits; position<bits.length; position++) {
            if (bits[position]) {
                setBit();
            }
            incrementPosition();
        }

        if (currentByte>autoWriteThreshhold)
            writeToStream();

    }

    private void writeToStream() throws IOException {
        outputStream.write(bitBuffer,0,currentByte);
        bitBuffer[0]=bitBuffer[currentByte];
        currentByte=0;
        Arrays.fill(bitBuffer,1,bitBuffer.length-1,ZERO);
    }

    private void incrementPosition() {
        currentByte+=bitsUsedinCurrentByte/7;
        bitsUsedinCurrentByte=(bitsUsedinCurrentByte+1)%8;
    }

    private void setBit() {
        bitBuffer[currentByte]|=BIT_MASK[bitsUsedinCurrentByte];
    }


    @Override
    public void close() throws IOException {
        while (bitsUsedinCurrentByte>0) {
            incrementPosition();
        }
        writeToStream();
        outputStream.close();
    }

    @Override
    public void flush() throws IOException {
        writeToStream();
        outputStream.flush();
    }
}
