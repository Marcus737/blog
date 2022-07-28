package com.wei.blogservice.service;

import com.wei.blogservice.entity.Msg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MsgService {
    void saveMsg(Msg msg);
    int getTotal();
    List<Msg> getMsgList(Integer pageNum, Integer pageSize);

    boolean delMsg(Integer msgId);
}
