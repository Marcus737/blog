package com.wei.blogservice.service.impl;

import com.wei.blogservice.entity.Msg;
import com.wei.blogservice.mappers.MsgMapper;
import com.wei.blogservice.service.MsgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MsgServiceImpl implements MsgService {

    @Resource
    MsgMapper msgMapper;

    @Override
    public int getTotal(){
        return msgMapper.getTotal();
    }

    @Override
    public void saveMsg(Msg msg) {
        msgMapper.saveMsg(msg);
    }

    @Override
    public List<Msg> getMsgList(Integer pageNum, Integer pageSize) {
        return msgMapper.getMsgList((pageNum - 1) * pageSize, pageSize);
    }

    @Override
    public boolean delMsg(Integer msgId) {
        int i = msgMapper.delMsg(msgId);
        if (i > 0)return true;
        return false;
    }
}
