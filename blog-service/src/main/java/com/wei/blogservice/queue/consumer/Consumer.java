package com.wei.blogservice.queue.consumer;

import lombok.Data;

/**
 * @author 凌雪
 */
@Data
public abstract class Consumer<T> implements Runnable {
    private T data;
}
