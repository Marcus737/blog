package com.wei.fileservice.util;

public class BandwidthLimiter {

    /**
     * KB
     * 速率单位
     * */
    private static final Long KB = 1024L;

    /**
     * The smallest count chunk length in bytes
     * 一次最少发送的字节数
     **/
    private static final Long CHUNK_LENGTH = 1024L;

    /**
     *  How many bytes will be sent or receive
     *  要被发送或接受的字节数
     * */
    private int bytesWillBeSentOrReceive = 0;

    /**
     * When the last piece was sent or receive
     * 上一次接受或发送的时间
     **/
    private long lastPieceSentOrReceiveTick = System.nanoTime();

    /**
     *  Default rate is 1024KB/s
     *  默认速率是1024KB/s
     **/
    private int maxRate = 1024;

    /**
     *  Time cost for sending CHUNK_LENGTH bytes in nanoseconds
     *  1 毫秒=1000000 纳秒
     *  发送最少字节数所需的纳秒
     **/
    private long timeCostPerChunk = (1000000000L * CHUNK_LENGTH) / (this.maxRate * KB);

    /**
     * Initialize a BandwidthLimiter object with a certain rate.
     *
     * @param maxRate the download or upload speed in KBytes
     */
    public BandwidthLimiter(int maxRate) {
        this.setMaxRate(maxRate);
    }

    /**
     * Set the max upload or download rate in KB/s. maxRate must be grater than
     * 0. If maxRate is zero, it means there is no bandwidth limit.
     *
     * @param maxRate If maxRate is zero, it means there is no bandwidth limit.
     * @throws IllegalArgumentException
     */
    public synchronized void setMaxRate(int maxRate) throws IllegalArgumentException {
        if (maxRate < 0) {
            throw new IllegalArgumentException(" maxRate can not less than 0 ");
        }
        this.maxRate = maxRate;
        if (maxRate == 0) {
            this.timeCostPerChunk = 0;
        } else {
            this.timeCostPerChunk = (1000000000L * CHUNK_LENGTH) / (this.maxRate * KB);
        }
    }

    /**
     * Next 1 byte should do bandwidth limit.
     * 读取下一个字节
     */
    public synchronized void limitNextBytes() {
        this.limitNextBytes(1);
    }

    /**
     * Next len bytes should do bandwidth limit
     * 读取下len个字节
     * @param  len 字节数
     */
    public synchronized void limitNextBytes(int len) {
        this.bytesWillBeSentOrReceive += len;

        //We have sent CHUNK_LENGTH bytes
        while (this.bytesWillBeSentOrReceive > CHUNK_LENGTH) {
            //现在时间
            long nowTick = System.nanoTime();
            //需要睡眠的时间=每次发送所需时间-(现在时间-上一次发送的时间)
            long missedTime = this.timeCostPerChunk - (nowTick - this.lastPieceSentOrReceiveTick);
            if (missedTime > 0) {
                try {
                    Thread.sleep(missedTime / 1000000, (int) (missedTime % 1000000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.bytesWillBeSentOrReceive -= CHUNK_LENGTH;
            this.lastPieceSentOrReceiveTick = nowTick + (missedTime > 0 ? missedTime : 0);
        }
    }
}