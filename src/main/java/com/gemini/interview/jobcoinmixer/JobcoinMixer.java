package com.gemini.interview.jobcoinmixer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.*;

public class JobcoinMixer implements Mixer {

    private static JobcoinMixer instance = new JobcoinMixer();
    public static JobcoinMixer getInstance() { return  instance; }

    public static void setMixer(JobcoinMixer aMixer) {
        instance = aMixer;
    }
    public static void reset() {
        setMixer(new JobcoinMixer());
    }

    private Stack<String> availableAddresses;
    private Map<String, Wallet> registar = Maps.newHashMap();
    private List<Jobcoin> transactionLog = Lists.newArrayList();

    private  JobcoinMixer() {
        init();
    }

    private void init() {
        availableAddresses = new Stack();
        try {
            String address;
            LineNumberReader fileReader = new LineNumberReader(new FileReader("src/main/resources/available-addresses.txt"));
            while ((address = fileReader.readLine()) != null) {
                availableAddresses.push(address);
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize jobcoin mixer: " + e);
        }
    }

    @Override
    public String register(List<String> addresses) {
        if (availableAddresses.isEmpty()) throw new IllegalStateException("Unable to register, there are no available addresses.");
        String uniqueAddress = availableAddresses.pop();
        registar.put(uniqueAddress, new Wallet(addresses));
        return uniqueAddress;
    }

    @Override
    public boolean launder(Jobcoin jobcoin) {
        if (registar.get(jobcoin.getTo()) != null) {
            //i want to redistribute right away evenly, but ultimately could be configurable
            registar.get(jobcoin.getTo()).add(jobcoin.getAmount(), Wallet.DistributionType.EVENLY);
            transactionLog.add(jobcoin);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<String> getAvailableLaundryAddresses() {
        return Collections.unmodifiableList(availableAddresses);
    }

    @Override
    public List<String> getUsedLaundryAddresses() {
        return Collections.unmodifiableList(Lists.newArrayList(registar.keySet()));
    }

    @Override
    public Wallet getWallet(String address) {
        return registar.get(address);
    }

    @Override
    public List<Jobcoin> getTransactionLog() {
        return transactionLog;
    }
}
