package phoenix.record.media;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface MovieWriter {

    public void writeFrame(int track, BufferedImage image, long duration) throws IOException;

    public void writeSample(int track, byte[] data, int off, int len, long duration, boolean isSync) throws IOException;

    public void writeSamples(int track, int sampleCount, byte[] data, int off, int len, long sampleDuration, boolean isSync) throws IOException;

    public void close() throws IOException;

    public boolean isVFRSupported();

    public boolean isDataLimitReached();
}
