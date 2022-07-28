package com.wei.fileservice.util;

import java.io.IOException;
import java.io.InputStream;

public class DownloadLimiter extends InputStream {
    private InputStream is;
    private BandwidthLimiter bandwidthLimiter;

    public DownloadLimiter(InputStream is, BandwidthLimiter bandwidthLimiter) {
        this.is = is;
        this.bandwidthLimiter = bandwidthLimiter;
    }

    @Override
    public int read() throws IOException {
        if (this.bandwidthLimiter != null) {
            this.bandwidthLimiter.limitNextBytes();
        }
        return this.is.read();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (bandwidthLimiter != null) {
            bandwidthLimiter.limitNextBytes(len);
        }
        return this.is.read(b, off, len);
    }
}