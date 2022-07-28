package com.wei.blogservice.mappers;

import com.wei.blogservice.entity.ControllerLog;
import org.apache.ibatis.annotations.Param;

/**
 * @author admin
 */
public interface LogMapper {

    /**
     * 32
     * @param log
     */
    void insertLog(@Param("log") ControllerLog log);
}
