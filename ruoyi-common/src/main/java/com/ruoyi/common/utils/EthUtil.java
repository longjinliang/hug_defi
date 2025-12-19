package com.ruoyi.common.utils;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.spring.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.http.HttpService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EthUtil
{

    private static Logger logger=LoggerFactory.getLogger(EthUtil.class);;
    public static String TRANSFER = "transfer";
    public static BigInteger GAS_LIMIT;

    public static Map<String,Web3j> web3jMap=new HashMap<>();

    
    public static Web3j getWeb3j(String chainNum) {
        Web3j web3j = web3jMap.get(chainNum);
        if(web3j==null){
            RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
            String nodeUrl=redisCache.getCacheObject(CacheConstants.SYS_CONFIG_KEY+chainNum);
            web3j=Web3j.build((Web3jService)new HttpService(nodeUrl));
            web3jMap.put(chainNum,web3j);
        }
    	return web3j;
    }


    public static BigInteger to16(String value) {
        if (value.startsWith("0x")) {
            value = value.replace("0x", "");
        }
        BigInteger bigInteger = new BigInteger(value, 16);
        return bigInteger;
    }


    public static BigDecimal getPancakePrice(Web3j web3j,String contractAddress, BigDecimal input, List<String> list,int tokenDecimal,int moneyDecimal) {
        try {
            String methodName = "getAmountsOut";
            List<Type> inputParameters = new ArrayList<Type>();
            inputParameters.add(new Uint256(input.multiply(new BigDecimal(Math.pow(10,tokenDecimal))).toBigInteger()));
            List<Address> collect = list.stream().map(item -> new Address(item)).collect(Collectors.toList());
            DynamicArray<Address> addrs = new DynamicArray(collect);
            inputParameters.add(addrs);
            List<TypeReference<?>> outputs = new ArrayList<TypeReference<?>>();
            TypeReference<DynamicArray<Uint256>> typeReference = new TypeReference<DynamicArray<Uint256>>() {};
            outputs.add(typeReference);
            Function function = new Function(methodName, inputParameters, outputs);
            String data = FunctionEncoder.encode(function);
            String value = ((EthCall)web3j.ethCall(Transaction.createEthCallTransaction(null, contractAddress, data), (DefaultBlockParameter)DefaultBlockParameterName.PENDING).send()).getValue();
            List<Type> result = FunctionReturnDecoder.decode(value, function.getOutputParameters());
            List<Uint256> blist = (List<Uint256>)result.get(0).getValue();
            List<BigDecimal> collect2 = blist.stream().map(item -> new BigDecimal(item.getValue().toString()).divide(new BigDecimal(Math.pow(10,moneyDecimal))).setScale(10, 1)).collect(Collectors.toList());
            return collect2.get(collect2.size()-1);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }


}
