package com.ruoyi.business.service;

import com.github.pagehelper.PageHelper;
import com.ruoyi.business.mapper.CUserGoogleMapper;
import com.ruoyi.business.mapper.CUserReceiveMapper;
import com.ruoyi.business.model.CUserGoogle;
import com.ruoyi.common.utils.EncrypUtils;
import com.ruoyi.common.utils.EthUtil;
import com.ruoyi.system.service.ISysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Credentials;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class RobotService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    UserService userService;

    @Autowired
    CUserReceiveMapper userReceiveMapper;
    @Autowired
    CUserGoogleMapper userGoogleMapper;
    @Autowired
    ISysConfigService sysConfigService;
    @Autowired
    CurrencyService currencyService;

    public CUserGoogle getPayAddress() {
        PageHelper.startPage(1,1);
        Example ex=new Example(CUserGoogle.class);
        ex.createCriteria().andEqualTo("type",0);
        ex.orderBy("updateTime").asc();
        List<CUserGoogle> list = userGoogleMapper.selectByExample(ex);
        CUserGoogle wallet = list.get(0);
        String privateKey = EncrypUtils.decode("12345678", wallet.getGoogleCode());
        Credentials credentials = Credentials.create(privateKey);
        wallet.setAddress(credentials.getAddress());
        wallet.setPrivateKey(privateKey);
        return wallet;
    }

    public CUserGoogle getMaintWallet() {
        Example ex=new Example(CUserGoogle.class);
        ex.createCriteria().andEqualTo("type",1);
        CUserGoogle wallet = userGoogleMapper.selectOneByExample(ex);
        String privateKey = EncrypUtils.decode("12345678", wallet.getGoogleCode());
        Credentials credentials = Credentials.create(privateKey);
        wallet.setAddress(credentials.getAddress());
        wallet.setPrivateKey(privateKey);
        return wallet;
    }

    public void updateWallet(CUserGoogle wallet) {
        userGoogleMapper.updateByPrimaryKeySelective(wallet);
    }

    public CUserGoogle getById(Long walletId) {
        CUserGoogle wallet = userGoogleMapper.selectByPrimaryKey(walletId);
        String privateKey = EncrypUtils.decode("12345678", wallet.getGoogleCode());
        Credentials credentials = Credentials.create(privateKey);
        wallet.setAddress(credentials.getAddress());
        wallet.setPrivateKey(privateKey);
        return wallet;
    }

    public List<CUserGoogle> getWalletList() {
        Example ex=new Example(CUserGoogle.class);
        ex.createCriteria().andEqualTo("type",0);
        ex.orderBy("id").asc();
        List<CUserGoogle> list = userGoogleMapper.selectByExample(ex);
        return list;
    }

    public List<CUserGoogle> getNotMappingWalletList() {
        Example ex=new Example(CUserGoogle.class);
        ex.createCriteria().andEqualTo("type",0)
                .andEqualTo("isMapping",0);

        ex.orderBy("id").asc();
        List<CUserGoogle> list = userGoogleMapper.selectByExample(ex);
        return list;
    }

    public List<CUserGoogle> getNotCollectWalletList() {
        Example ex=new Example(CUserGoogle.class);
        ex.createCriteria().andEqualTo("type",0)
                .andEqualTo("isCollect",0)
                        .andEqualTo("isMapping",1);

        ex.orderBy("id").asc();
        List<CUserGoogle> list = userGoogleMapper.selectByExample(ex);
        return list;
    }
}
