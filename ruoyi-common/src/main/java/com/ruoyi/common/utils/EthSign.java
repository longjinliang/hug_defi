package com.ruoyi.common.utils;

import com.ruoyi.common.utils.spring.SpringUtils;
import org.springframework.security.crypto.codec.Hex;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.*;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

public class EthSign {

  public static boolean validate(String signature, String message, String address)
  {
	  return true;
  }


	public static String getWithdrawSign(Integer poolType,String address, BigInteger amount, Integer nonce, long timeout, String privateKey) {


	   return null;
  }

	public static String getPayFeeSign(String userAddress, BigInteger bigAmount, BigInteger tokenAmount,long timeout, int nonce,String privateKey) {
		return null;
	}

	public static String getBuySign(String userAddress, BigInteger usdtBigAmount,
									BigInteger tokenAmount,
									String parentAddress,BigInteger parentBigAmount,
									String nodeAddress,BigInteger nodeBigAmount,
									long timeout, int nonce, String privateKey) {
		return null;
	}
}