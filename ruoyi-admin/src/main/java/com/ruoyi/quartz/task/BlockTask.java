package com.ruoyi.quartz.task;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ruoyi.business.mapper.CUserStaticsMapper;
import com.ruoyi.business.model.*;
import com.ruoyi.business.service.*;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.EthUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Component("blockTask")
public class BlockTask implements CommandLineRunner
{

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ISysConfigService sysConfigService;


    @Autowired
    ArbBlockNumService arbBlockNumService;


    @Autowired
    private UserService userService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    BalanceService balanceService;


    @Autowired
    RedisCache redisCache;



    @Autowired
    UserStaticsService userStaticsService;


    @Autowired
    WithdrawService withdrawService;

    @Autowired
    PayFeeService payFeeService;

    @Autowired
    RobotService robotService;

    @Autowired
    CUserStaticsMapper userStaticsMapper;

    @Autowired
    BuyService buyService;





    @PostConstruct
    public void init() throws Exception {
//        handleScanArbEvent(47446208L);
//        https://bsc-testnet.infura.io/v3/abbaae8eb3b64ca1b8b7c85c349edff0
//        scanArbEvent();
//        scanWithdraw();
//        subEffect();
//        updateLpBalance();
//        updateFeeSum();
//          updateTokenSnap();
//        scanGameSettlementOrder();
//        rewardInterest();
//        rewardTeam();
//        rewardSort();
//        scanReward();
//          confirmWithdraw();

//        robot();
//        bindParent();

//        collectRobotEth();

//        updateIpUsers();

//        updateUserMapping();
//        updateBlackMapping();

//        robotMapping();
//        updatePrice();

//        redisCache.deleteObject("handle_from_block_num");
//        redisCache.deleteObject("handle_to_block_num");


    }



    public void scanArbEvent() throws IOException {
        logger.info("扫描合约事件中=======");

        // 创建 Web3j 实例
        Web3j web3j = EthUtil.getWeb3j("chain_arb_rpc");

        BigInteger blockNumber = web3j.ethBlockNumber().send().getBlockNumber();
        long bnum = blockNumber.longValue();

        long min = 0;

        Integer maxNum = arbBlockNumService.getMaxNum();
        if (maxNum != null) {
            min = maxNum.longValue() - 10;
        }


        String ct = sysConfigService.selectConfigByKey("contract_address");



        if (StringUtils.isEmpty(ct)) {
            return;
        }
        List<String> contracts = Arrays.asList(ct);

        long from = min + 1;

        long to = 0;


        while (true) {
            if (to != 0) {
                from = to;
            }
            to = from + 100;

            if (from > bnum) {
                break;
            }
            if (to > bnum) {
                to = bnum;
            }
            if (from > to) {
                break;
            }

            logger.info("scan  arb blockNumber:" + from + "----" + to);

            // 设置过滤条件

            EthFilter filter = new EthFilter(DefaultBlockParameter.valueOf(BigInteger.valueOf(from)), DefaultBlockParameter.valueOf(BigInteger.valueOf(to)), contracts);

            // 获取符合条件的日志
            EthLog logs = web3j.ethGetLogs(filter).send();
            if(logs.hasError()){
                throw new RuntimeException(logs.getError().getMessage());
            }

            if (logs != null) {
                // 解析日志数据
                List<EthLog.LogResult> logResults = logs.getLogs();
                if(logResults !=null){
                    for (EthLog.LogResult logResult : logResults) {
//                Log eventLog = (Log) logResult.get();
                        EthLog.LogObject eventLog = (EthLog.LogObject) logResult.get();
                        String blockHash = eventLog.getBlockHash();
                        long bn = eventLog.getBlockNumber().longValue();
                        List<String> topics = eventLog.getTopics();
                        String methodHash = topics.get(0);
                        String contract = eventLog.getAddress();
                        String hash = eventLog.getTransactionHash();

                        String eventData = eventLog.getData().replace("0x", "");
                        // TODO: 处理数据
                        if (methodHash.equalsIgnoreCase("0x76c3da0779d2bcebe57c00bd8fc4cf03dfead2becd6568e7287bfe788ab3fa6f")) {
                            //支付日志
                            String fromAddress = "0x" + topics.get(1).substring(26);
                            BigDecimal amount = new BigDecimal(EthUtil.to16("0x" + topics.get(2).substring(26)).toString());

                            amount = amount.divide(new BigDecimal(Math.pow(10, 18)), 10, BigDecimal.ROUND_DOWN);


                            String toAddress = "0x" + topics.get(3).substring(26);


                            BigDecimal tokenAmount=new BigDecimal(EthUtil.to16("0x"+eventData).toString());

                            tokenAmount = tokenAmount.divide(new BigDecimal(Math.pow(10, 18)), 10, BigDecimal.ROUND_DOWN);


                            payFeeService.savePayFee(fromAddress,amount,toAddress,hash,tokenAmount);

                        }else  if (methodHash.equalsIgnoreCase("0x8166bf25f8a2b7ed3c85049207da4358d16edbed977d23fa2ee6f0dde3ec2132")) {
                            //领取奖励

                            String fromAddress = "0x" + topics.get(1).substring(26);
                            BigDecimal amount = new BigDecimal(EthUtil.to16("0x" + topics.get(2).substring(26)).toString());

                            int poolType = EthUtil.to16("0x" + topics.get(3).substring(26)).intValue();


                            if(poolType==1){
                                CCurrency usdt = currencyService.getCurrency("USDT");
                                amount = amount.divide(new BigDecimal(Math.pow(10, usdt.getDecimals())), 10, BigDecimal.ROUND_DOWN);
                            }else if(poolType==2||poolType==3){
                                CCurrency hug = currencyService.getCurrency("HUG");
                                amount = amount.divide(new BigDecimal(Math.pow(10, hug.getDecimals())), 10, BigDecimal.ROUND_DOWN);
                            }else{
                                continue;
                            }

                            withdrawService.saveOrder(fromAddress,amount,hash,poolType);

                        }else  if (methodHash.equalsIgnoreCase("0x232d0afc06684be9cf9a696246e0847daeafaa88938dacb7b4e1d3e0fb20cd79")) {
                            //领取奖励

                            String fromAddress = "0x" + topics.get(1).substring(26);
                            CCurrency usdtCurrency = currencyService.getCurrency("USDT");
                            CCurrency hugCurrency = currencyService.getCurrency("HUG");
                            BigDecimal usdtAmount = new BigDecimal(EthUtil.to16("0x" + topics.get(2).substring(26)).toString());
                            usdtAmount=usdtAmount.divide(new BigDecimal(Math.pow(10, usdtCurrency.getDecimals())),10, BigDecimal.ROUND_DOWN);

                            BigDecimal tokenAmount = new BigDecimal(EthUtil.to16("0x" + topics.get(3).substring(26)).toString());
                            tokenAmount=tokenAmount.divide(new BigDecimal(Math.pow(10, hugCurrency.getDecimals())),10, BigDecimal.ROUND_DOWN);

                            String parentAddress = "0x" + eventData.substring(0,64).substring(24);
                            BigDecimal parentAmount = new BigDecimal(EthUtil.to16(eventData.substring(64,128)).toString())
                                    .divide(new BigDecimal(Math.pow(10,usdtCurrency.getDecimals())),10, BigDecimal.ROUND_DOWN);

                            String nodeAddress = "0x" + eventData.substring(128,192).substring(24);
                            BigDecimal nodeAmount = new BigDecimal(EthUtil.to16(eventData.substring(192)).toString())
                                    .divide(new BigDecimal(Math.pow(10,usdtCurrency.getDecimals())),10, BigDecimal.ROUND_DOWN);

                            buyService.saveOrder(fromAddress,usdtAmount,tokenAmount,hash,parentAddress,parentAmount,nodeAddress,nodeAmount);
                        }
                    }
                }
            }

            CArbBlockNum blockNum = new CArbBlockNum();
            blockNum.setNum(to);
            blockNum.setCreateTime(new Date());
            blockNum.setUpdateTime(new Date());
            blockNum.setBlockHash(from + "_" + to);
            arbBlockNumService.insertBlock(blockNum);

            if (to == bnum) {
                break;
            }
        }
    }











    public void updatePrice()
    {
        {
            logger.info("扫描更新ETH价格中==========");
            List<String> contracts = Arrays.asList("0x2170Ed0880ac9A755fd29B2688956BD959F933F8", "0x55d398326f99059fF775485246999027B3197955");
            String router = "0x10ED43C718714eb63d5aA57B78B54704E256024E";
            Web3j web3j = EthUtil.getWeb3j("chain_bsc_rpc");
            BigDecimal pancakePrice = EthUtil.getPancakePrice(web3j, router, BigDecimal.ONE, contracts, 18, 18);
            if (pancakePrice != null) {
                CCurrency ethCy = currencyService.getCurrency("ETH");
                ethCy.setUsdtPrice(pancakePrice);
                ethCy.setUpdateTime(new Date());
                currencyService.update(ethCy);
            }
        }

    }


    public void updateCacheTotalInfo(){

        JSONObject obj = new JSONObject();

            //参与用户数
            Integer userCount = payFeeService.getUserCount();

            //交互次数
            Integer payCount = payFeeService.getPayCount();

            BigDecimal rewardUsdtSum=payFeeService.getRewardUsdtSum();
            if(rewardUsdtSum==null){
                rewardUsdtSum=new BigDecimal(0);
            }

            obj.put("userCount",userCount);
            obj.put("payCount",payCount);
            obj.put("rewardUsdtSum",rewardUsdtSum);

            redisCache.setCacheObject("getPayFeeInfo",JSON.toJSONString(obj));

        logger.info("更新统计缓存成功====");

    }


    public void updateCacheOrderList(){
        logger.info("更新订单列表缓存开始====");
        int pageNum = 1;
        int pageSize = 10;
        List<JSONObject> list=payFeeService.getPayFeeList(pageNum,pageSize, null);
        PageInfo<JSONObject> pageInfo = new PageInfo<>(list);
        redisCache.setCacheObject("getPayFeeList",pageInfo);
        logger.info("更新订单列表缓存结束====");
    }









    public static void main(String[] args) throws IOException {
        System.out.println("000000000000000000000000bc7d7709d2538b79a1015b0533ef4cfc8796cb2c".substring(0,64).substring(24));

    }
















    @Override
    public void run(String... args) throws Exception {

    }



}
