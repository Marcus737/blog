package com.wei.blogservice.queue.consumer;

import com.wei.blogservice.entity.ControllerLog;
import com.wei.blogservice.mappers.LogMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 凌雪
 */

@Component
@Scope("prototype")
public class SqlConsumer<T> extends Consumer<T>  {

    @Resource
    LogMapper mapper;

    @Override
    public void run() {
        mapper.insertLog((ControllerLog) getData());
    }
}
