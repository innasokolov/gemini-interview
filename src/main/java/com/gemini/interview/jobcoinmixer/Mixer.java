package com.gemini.interview.jobcoinmixer;

import java.util.List;

public interface Mixer {
    String register(List<String> addresses);
    boolean launder(Jobcoin jobcoin);
    List<String> getAvailableLaundryAddresses();
    List<String> getUsedLaundryAddresses();
    Wallet getWallet(String address);
    List<Jobcoin> getTransactionLog();
}
