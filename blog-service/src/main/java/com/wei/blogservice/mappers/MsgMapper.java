package com.wei.blogservice.mappers;

import com.wei.blogservice.entity.Msg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MsgMapper {

    void saveMsg(@Param("msg") Msg msg);

    List<Msg> getMsgList(@Param("start") Integer start, @Param("size") Integer size);

    int getTotal();

    int delMsg(@Param("msgId") Integer msgId);
}
