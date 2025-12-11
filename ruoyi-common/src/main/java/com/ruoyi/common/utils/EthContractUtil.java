
package com.ruoyi.common.utils;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class EthContractUtil extends ManagedTransaction {
    public static final BigInteger GAS_LIMIT = BigInteger.valueOf(21000L);

    public EthContractUtil(Web3j web3j, TransactionManager transactionManager) {
        super(web3j, transactionManager);
    }

    private TransactionReceipt send(String toAddress, BigDecimal value, Convert.Unit unit) throws IOException, InterruptedException, TransactionException {
        BigInteger gasPrice = this.requestCurrentGasPrice();
        return this.send(toAddress, value, unit, gasPrice, GAS_LIMIT);
    }

    private TransactionReceipt send(String toAddress, BigDecimal value, Convert.Unit unit, BigInteger gasPrice, BigInteger gasLimit) throws IOException, InterruptedException, TransactionException {
        BigDecimal weiValue = Convert.toWei(value, unit);
        if (!Numeric.isIntegerValue(weiValue)) {
            throw new UnsupportedOperationException("Non decimal Wei value provided: " + value + " " + unit.toString() + " = " + weiValue + " Wei");
        } else {
            String resolvedAddress = this.ensResolver.resolve(toAddress);
            return this.send(resolvedAddress, "", weiValue.toBigIntegerExact(), gasPrice, gasLimit);
        }
    }

    public static RemoteCall<TransactionReceipt> sendFunds(Web3j web3j, Credentials credentials, String toAddress, BigDecimal value, Convert.Unit unit) throws InterruptedException, IOException, TransactionException {
        TransactionManager transactionManager = new RawTransactionManager(web3j, credentials,5,4000);
        return new RemoteCall(() -> {
            return (new EthContractUtil(web3j, transactionManager)).send(toAddress, value, unit);
        });
    }

    public RemoteCall<TransactionReceipt> sendFunds(String toAddress, BigDecimal value, Convert.Unit unit) {
        return new RemoteCall(() -> {
            return this.send(toAddress, value, unit);
        });
    }

    public RemoteCall<TransactionReceipt> sendFunds(String toAddress, BigDecimal value, Convert.Unit unit, BigInteger gasPrice, BigInteger gasLimit) {
        return new RemoteCall(() -> {
            return this.send(toAddress, value, unit, gasPrice, gasLimit);
        });
    }

    public static RemoteCall<TransactionReceipt> sendFunds(Web3j web3j, Credentials credentials,String to, String data, BigInteger value, BigInteger gasPrice, BigInteger gasLimit) {
        TransactionManager transactionManager = new RawTransactionManager(web3j, credentials,3,3000);
        return new RemoteCall(() -> {
            return new EthContractUtil(web3j, transactionManager).send( to,  data,  value,  gasPrice,  gasLimit);
        });
    }


}
