package com.ruoyi.business.service;


import com.ruoyi.business.mapper.CArbBlockNumMapper;
import com.ruoyi.business.model.CArbBlockNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional
public class ArbBlockNumService {

  @Autowired
  private CArbBlockNumMapper blockNumMapper;




  public CArbBlockNum getBlockNum(int num){
    Example ex=new Example(CArbBlockNum.class);
    ex.createCriteria().andEqualTo("num",num);
    List<CArbBlockNum> list = blockNumMapper.selectByExample(ex);
    if(list.size()>0){
      return list.get(0);
    }
    return null;
  }


  public void insertBlock(CArbBlockNum blockNum) {
    blockNumMapper.insertSelective(blockNum);
  }

  public Integer getMaxNum() {
    return blockNumMapper.getMaxNum();
  }
}
