package com.ruoyi.business.service;

import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ruoyi.business.mapper.CBindRecordMapper;
import com.ruoyi.business.mapper.CUserMapper;
import com.ruoyi.business.model.CBindRecord;
import com.ruoyi.business.model.CUser;
import com.ruoyi.common.utils.ip.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CUserMapper userMapper;

    @Autowired
    CBindRecordMapper bindRecordMapper;


    public CUser getByAddress(String address) {
        Example ex=new Example(CUser.class);
        ex.createCriteria().andEqualTo("address",address);
        return userMapper.selectOneByExample(ex);

    }

    public CUser addUser(String address) {
        CUser user=new CUser();
        user.setUserName(address);
        user.setNickName("小可爱");
        user.setUserType("01");
        try {
            user.setLoginIp(IpUtils.getIpAddr());
        }catch (Exception e){
            e.printStackTrace();
            user.setLoginIp(null);
        }
        user.setLoginDate(new Date());
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setAddress(address);
        user.setStatus("0");
        userMapper.insertSelective(user);
        user.setUserTree(user.getUserId()+"");
        user.setUserDepth(1);
        userMapper.updateByPrimaryKeySelective(user);
        return user;
    }

    public CUser bindParent(String address, String parentAddress) {

        CUser user = getByAddress(address);
        if(user !=null){
            return user;
        }
        CUser parent = getByAddress(parentAddress);
         user=new CUser();
        user.setUserName(address);
        user.setNickName("");
        user.setUserType("01");
        try {
            user.setLoginIp(IpUtils.getIpAddr());
        }catch (Exception ex){
            user.setLoginIp("0.0.0.0");
        }

        user.setLoginDate(new Date());
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setAddress(address);
        user.setStatus("0");
        userMapper.insertSelective(user);
        user.setUserTree(parent.getUserTree()+"-"+user.getUserId());
        user.setParentId(parent.getUserId());
        user.setParentAddress(parentAddress);
        user.setUserDepth(user.getUserTree().split("-").length);
        userMapper.updateByPrimaryKeySelective(user);
        return user;
    }

    public CUser bindParent(String address, String parentAddress,String hash) {

        Example example=new Example(CBindRecord.class);
        example.createCriteria().andEqualTo("address",address);
        int count = bindRecordMapper.selectCountByExample(example);
        if(count>0){
            return null;
        }

        CUser user = getByAddress(address);
        if(user==null){
            addUser(address);
            user = getByAddress(address);
        }

        CUser parent = getByAddress(parentAddress);

        user.setUserTree(parent.getUserTree()+"-"+user.getUserId());
        user.setParentId(parent.getUserId());
        user.setParentAddress(parentAddress);
        user.setUserDepth(user.getUserTree().split("-").length);
        userMapper.updateByPrimaryKeySelective(user);

        CBindRecord record=new CBindRecord();
        record.setAddress(address);
        record.setParentAddress(parentAddress);
        record.setHash(hash);
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        bindRecordMapper.insertSelective(record);

        return user;
    }

    public List<JSONObject> getDirectList(String userAddress) {
        Example ex=new Example(CUser.class);
        ex.createCriteria().andEqualTo("parentAddress",userAddress);
        List<CUser> list = userMapper.selectByExample(ex);
        return list.stream().map(item->{
            JSONObject obj = new JSONObject();
            obj.put("createTime",item.getCreateTime());
            obj.put("address",item.getAddress());
            return obj;
        }).collect(Collectors.toList());
    }

    public CUser getById(Long parentId) {
        return userMapper.selectByPrimaryKey(parentId);
    }

    public void updateUser(CUser user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    public List<JSONObject> getBackUserList(String address, String parentAddress, Date startTime, Date endTime, Integer nodeLevel) {
        return  userMapper.getBackUserList(address,parentAddress,startTime,endTime,nodeLevel);
    }

    public List<CUser> getNotCountUserList() {
        Example ex=new Example(CUser.class);
        ex.createCriteria().andEqualTo("isCount",0);
        return userMapper.selectByExample(ex);
    }

    public int getDirectCount(String address) {
        Example ex=new Example(CUser.class);
        ex.createCriteria().andEqualTo("parentAddress",address);
        return userMapper.selectCountByExample(ex);
    }

    public int getTeamCount(String userTree) {
        return userMapper.getTeamCount(userTree);
    }

    public List<CUser> getListByIds(List<Long> list) {
        Example ex=new Example(CUser.class);
        ex.createCriteria().andIn("userId",list);
        ex.orderBy("userId").asc();
        return userMapper.selectByExample(ex);
    }

    public List<CUser> getNotTokenAmountUserList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return userMapper.getNotTokenAmountUserList();
    }

    public List<CUser> getNodeUserList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return userMapper.getNodeUserList();
    }

    public CUser getByEmail(String email) {
        Example ex=new Example(CUser.class);
        ex.createCriteria().andEqualTo("email",email);
        return userMapper.selectOneByExample(ex);
    }

    public List<CUser> getDirectUserList(String address) {
        Example ex=new Example(CUser.class);
        ex.createCriteria().andEqualTo("parentAddress",address);
        return userMapper.selectByExample(ex);
    }

    public CUser getBigUser(String address) {
        return userMapper.getBigUser(address);
    }

    public int getCountByIp(String ip) {
        return userMapper.getCountByIp(ip);
    }

    public int getChildCountByLevel(String userTree, Integer userDepth, int depth) {
        return userMapper.getChildCountByLevel(userTree,userDepth,depth);
    }

    public List<JSONObject> getDirectCountList() {
        return userMapper.getDirectCountList();
    }

    public int getDirectCountByTime(String address, Date date) {
        Example ex=new Example(CUser.class);
        ex.createCriteria().andEqualTo("parentAddress",address)
                .andLessThan("createTime",date);
        return userMapper.selectCountByExample(ex);
    }

    public int getVerifiedCount(String address) {
        return userMapper.getVerifiedCount(address);
    }
}
