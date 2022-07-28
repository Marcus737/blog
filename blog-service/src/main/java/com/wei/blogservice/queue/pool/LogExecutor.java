package com.wei.blogservice.queue.pool;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author admin
 */


@Component
public class LogExecutor  {

    @Value("${threadPool.corePoolSize}")
    private Integer corePoolSize;
    @Value("${threadPool.maximumPoolSize}")
    private Integer maximumPoolSize;
    @Value("${threadPool.keepAliveTime}")
    private Long keepAliveTime;
    @Value("${threadPool.maxTaskNum}")
    private Integer maxTaskNum;

    private ThreadPoolExecutor threadPoolExecutor;

    public LogExecutor() {
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        if (threadPoolExecutor == null) {
            synchronized (LogExecutor.class) {
                if (threadPoolExecutor == null) {
                    threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,
                            maximumPoolSize,
                            keepAliveTime,
                            TimeUnit.SECONDS,
                            new ArrayBlockingQueue<>(maxTaskNum),
                            new ThreadPoolExecutor.DiscardPolicy());
                }
            }
        }
        return threadPoolExecutor;
    }
}
