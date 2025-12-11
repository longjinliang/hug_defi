package com.ruoyi.business.service;

import com.ruoyi.business.mapper.CBalanceMapper;
import com.ruoyi.business.mapper.CUserStaticsMapper;
import com.ruoyi.business.mapper.CUserTmpMapper;
import com.ruoyi.business.mapper.CWithdrawMapper;
import com.ruoyi.business.model.*;
import com.ruoyi.common.utils.EncrypUtils;
import com.ruoyi.common.utils.IdWorker;
import com.ruoyi.system.domain.SysConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserTempService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CUserTmpMapper userTmpMapper;
    @Autowired
    CBalanceMapper balanceMapper;

    @Autowired
    CUserStaticsMapper userStaticsMapper;



    @Autowired
    CWithdrawMapper withdrawMapper;


//    public CUserTmp getUserTemp(Long businessId,String type){
//        Example ex=new Example(CUserTmp.class);
//        ex.createCriteria().andEqualTo("businessId",businessId)
//                .andEqualTo("businessType",type);
//        CUserTmp userTmp = userTmpMapper.selectOneByExample(ex);
//        return userTmp;
//    }
//
//    public void addUserTmp(Long businessId,String type,String info){
//        CUserTmp userTmp=new CUserTmp();
//        userTmp.setBusinessId(businessId);
//        userTmp.setBusinessType(type);
//        userTmp.setInfo(EncrypUtils.encode( "123456",info));
//        userTmp.setStatus( 1);
//        userTmp.setOid(IdWorker.next(businessId));
//        userTmp.initFingerprint();
//        userTmpMapper.insertSelective(userTmp);
//    }
//
//    public void updateUserTmp(Long businessId,String type,String info){
//        CUserTmp userTmp = getUserTemp(businessId,type);
//        userTmp.setInfo(EncrypUtils.encode( "123456",info )
//        );
//        userTmp.setUpdateTime(new Date());
//        userTmp.initFingerprint();
//        userTmpMapper.updateByPrimaryKeySelective(userTmp);
//    }
//
//    public void updateBalance(CBalance balance){
//        balance=balanceMapper.selectByPrimaryKey(balance.getId());
//        CUserTmp userTemp = getUserTemp(balance.getId(), "t1");
//        String info=balance.getId()+"_"+balance.getAddress()+"_"+balance.getCurrency()+"_"+balance.getBalanceType()+"_"+balance.getAmount().stripTrailingZeros().toString();
//        if(userTemp==null){
//            addUserTmp(balance.getId(),"t1",info);
//        }else{
//            updateUserTmp(balance.getId(),"t1",info);
//        }
//    }
//
//    public boolean checkBalance(CBalance balance){
//        boolean flag=false;
//        CUserTmp userTmp = getUserTemp(balance.getId(),"t1");
//        if(userTmp==null){
//            updateBalance(balance);
//            userTmp = getUserTemp(balance.getId(),"t1");
//        }
//        String info="";
//        if(userTmp!=null && userTmp.getCheckFingerprint()){
//             info=EncrypUtils.decode("123456",userTmp.getInfo());
//            String[] infoArr = info.split("_");
//            if(Long.valueOf(infoArr[0]).longValue()==balance.getId().longValue()
//                    && balance.getAddress().equalsIgnoreCase(infoArr[1])
//                    && balance.getCurrency().equalsIgnoreCase(infoArr[2])
//                    && balance.getBalanceType().equalsIgnoreCase(infoArr[3])
//                    && balance.getAmount().stripTrailingZeros().toString().equalsIgnoreCase(infoArr[4])){
//                flag=true;
//            }
//        }
//        if(!flag){
//            logger.info("user balance exception,tempinfo:{}",info);
//            logger.info(" user balance exception, id = {}, currency = {}, address = {},amount= {}", balance.getId(), balance.getCurrency(), balance.getAddress(),balance.getAmount());
//
//            logger.error("user balance exception,tempinfo:{}",info);
//            logger.error(" user balance exception, id = {}, currency = {}, address = {},amount= {}", balance.getId(), balance.getCurrency(), balance.getAddress(),balance.getAmount());
//
//            throw new RuntimeException("用户资产异常");
//        }
//        return flag;
//    }
//
//    public void updateUserStatics(CUserStatics obj){
//        obj=userStaticsMapper.selectByPrimaryKey(obj.getId());
//        CUserTmp userTemp = getUserTemp(obj.getId(), "t2");
//        String info=obj.getId()+"_"+obj.getTeamLevel()+"_"+obj.getTeamAmount().stripTrailingZeros();
//        if(userTemp==null){
//            addUserTmp(obj.getId(),"t2",info);
//        }else{
//            updateUserTmp(obj.getId(),"t2",info);
//        }
//    }
//
//    public boolean checkUserStatics(CUserStatics userStatics){
//        boolean flag=false;
//        CUserTmp userTmp = getUserTemp(userStatics.getId(),"t2");
//        if(userTmp==null){
//            updateUserStatics(userStatics);
//            userTmp = getUserTemp(userStatics.getId(), "t2");
//        }
//        String info="";
//        if(userTmp!=null && userTmp.getCheckFingerprint()){
//            info=EncrypUtils.decode("123456",userTmp.getInfo());
//            String[] infoArr = info.split("_");
//            if(Long.valueOf(infoArr[0]).longValue()==userStatics.getId().longValue()
//                    && userStatics.getTeamLevel().toString().equalsIgnoreCase(infoArr[1])
//                    && userStatics.getTeamAmount().stripTrailingZeros().toString().equalsIgnoreCase(infoArr[2])
//                    ){
//                flag=true;
//            }
//        }
//        if(!flag){
//            logger.info("checkUserStatics exception,tempinfo:{}",info);
//            logger.info("checkUserStatics exception, id = {}, address = {}, teamLevel = {},teamAmountUsdt= {},teamBigAmountUsdt= {},teamSmallAmountUsdt= {},outTime= {}", userStatics.getId(),userStatics.getAddress(), userStatics.getTeamLevel(),userStatics.getTeamAmount());
//
//            logger.error("checkUserStatics exception,tempinfo:{}",info);
//            logger.error("checkUserStatics exception, id = {}, address = {}, teamLevel = {},teamAmountUsdt= {},teamBigAmountUsdt= {},teamSmallAmountUsdt= {},outTime= {}", userStatics.getId(), userStatics.getAddress(),userStatics.getTeamLevel(),userStatics.getTeamAmount());
//
//            throw new RuntimeException("用户数据异常");
//        }
//        return flag;
//    }
//
//
//
//
//
//    public void updateConfig(SysConfig obj){
//        CUserTmp userTemp = getUserTemp(obj.getConfigId(), "t7");
//        String info=obj.getConfigId()+"|"+obj.getConfigValue();
//        if(userTemp==null){
//            addUserTmp(obj.getConfigId(),"t7",info);
//        }else{
//            updateUserTmp(obj.getConfigId(),"t7",info);
//        }
//    }
//
//    public boolean checkConfig(SysConfig obj){
//        boolean flag=false;
//        CUserTmp userTmp = getUserTemp(obj.getConfigId(),"t7");
//        if(userTmp==null){
//            updateConfig(obj);
//            userTmp = getUserTemp(obj.getConfigId(), "t7");
//        }
//        String info="";
//        if(userTmp!=null && userTmp.getCheckFingerprint()){
//            info=EncrypUtils.decode("123456",userTmp.getInfo());
//            String[] infoArr = info.split("\\|");
//            if(Long.valueOf(infoArr[0]).longValue()==obj.getConfigId().longValue()
//                    && obj.getConfigValue().equalsIgnoreCase(infoArr[1])){
//                flag=true;
//            }
//        }
//        if(!flag){
//            logger.info("checkConfig exception,tempinfo:{}",info);
//            logger.info(" checkConfig, id = {},value= {}", obj.getConfigId(), obj.getConfigValue());
//
//            logger.error("checkConfig,tempinfo:{}",info);
//            logger.error(" checkConfig, id = {}, value = {}", obj.getConfigId(), obj.getConfigValue());
//
//            throw new RuntimeException("检查缓存配置异常");
//        }
//        return flag;
//    }
//
//    public void updateTeamConfig(CTeamConfig obj){
//        CUserTmp userTemp = getUserTemp(obj.getId(), "t8");
//        String info=obj.getId()+"_"+obj.getLevel();
//        if(userTemp == null){
//            addUserTmp(obj.getId(),"t8",info);
//        }else{
//            updateUserTmp(obj.getId(),"t8",info);
//        }
//    }
//
//    public boolean checkTeamConfig(CTeamConfig obj){
//        boolean flag=false;
//        CUserTmp userTmp = getUserTemp(obj.getId(),"t8");
//        if(userTmp==null){
//            updateTeamConfig(obj);
//            userTmp = getUserTemp(obj.getId(), "t8");
//        }
//        String info="";
//        if(userTmp!=null && userTmp.getCheckFingerprint()){
//            info=EncrypUtils.decode("123456",userTmp.getInfo());
//            String[] infoArr = info.split("_");
//            if(Long.valueOf(infoArr[0]).longValue()==obj.getId().longValue()
//                    && obj.getLevel().toString().equalsIgnoreCase(infoArr[1])
//            ){
//                flag=true;
//            }
//        }
//        if(!flag){
//            logger.info("checkTeamConfig exception,tempinfo:{}",info);
//            logger.info(" checkTeamConfig, id = {},level={},rewardRatio= {}", obj.getId(),obj.getLevel());
//
//            logger.error("checkTeamConfig,tempinfo:{}",info);
//            logger.error(" checkTeamConfig, id = {},level={},rewardRatio= {}", obj.getId(),obj.getLevel());
//
//            throw new RuntimeException("检查团队配置异常");
//        }
//        return flag;
//    }
//
//    public void updateWithdraw(CWithdraw obj){
//        obj=withdrawMapper.selectByPrimaryKey(obj.getId());
//        CUserTmp userTemp = getUserTemp(obj.getId(), "t9");
//        String info=obj.getId()+"_"+obj.getStatus()+"_"+obj.getLastAmount().stripTrailingZeros()
//                +"_"+obj.getAddress();
//        if(userTemp==null){
//            addUserTmp(obj.getId(),"t9",info);
//        }else{
//            updateUserTmp(obj.getId(),"t9",info);
//        }
//    }
//
//    public boolean checkWithdraw(CWithdraw order){
//        boolean flag=false;
//        CUserTmp userTmp = getUserTemp(order.getId(),"t9");
//        if(userTmp==null){
//            updateWithdraw(order);
//            userTmp = getUserTemp(order.getId(), "t9");
//        }
//        String info="";
//        if(userTmp!=null && userTmp.getCheckFingerprint()){
//            info=EncrypUtils.decode("123456",userTmp.getInfo());
//            String[] infoArr = info.split("_");
//            if(Long.valueOf(infoArr[0]).longValue()==order.getId().longValue()
//                    && order.getStatus().toString().equalsIgnoreCase(infoArr[1])
//                    && order.getLastAmount().stripTrailingZeros().toString().equalsIgnoreCase(infoArr[2])
//                    && order.getAddress().equalsIgnoreCase(infoArr[3])
//
//            ){
//                flag=true;
//            }
//        }
//        if(!flag){
//            logger.info("checkWithdraw exception,tempinfo:{}",info);
//            logger.info("checkWithdraw exception, id = {}, address = {}, status= {},lastAmount:{}", order.getId(), order.getAddress(),order.getStatus(),order.getLastAmount());
//
//            logger.error("checkWithdraw exception,tempinfo:{}",info);
//            logger.error("checkWithdraw exception, id = {}, address = {}, status= {},lastAmount:{}", order.getId(), order.getAddress(),order.getStatus(),order.getLastAmount());
//
//            throw new RuntimeException("用户提币数据异常");
//        }
//        return flag;
//    }


}
