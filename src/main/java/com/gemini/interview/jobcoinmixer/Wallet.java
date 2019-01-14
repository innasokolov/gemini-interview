package com.gemini.interview.jobcoinmixer;

import com.google.common.collect.Maps;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Wallet {
    public enum DistributionType { EVENLY, TO_EMPTIEST }
    private Map<String, Double> money = Maps.newHashMap();

    public Wallet(List<String> addresses) {
        if (addresses != null) {
            for (String address : addresses) money.put(address, Double.valueOf(0.0));
        }
    }

    public Wallet(Map<String, Double> ialreadyhavesome) {
        if (ialreadyhavesome != null) {
            for (String address : ialreadyhavesome.keySet()) money.put(address, ialreadyhavesome.get(address));
        }
    }

    public void add(double amount, DistributionType type) {
        switch (type) {
            case TO_EMPTIEST:
                addToEmptiest(amount);
                break;
            case EVENLY:
            default:
                addEvenly(amount);
        }
    }

    public Double get(String address) {
        return money.get(address);
    }

    public Double get() {
        Double total = Double.valueOf(0.0);
        for (Double next : money.values()) total = Double.sum(total, next);
        return total;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "money=" + money +
                '}';
    }

    private void addToEmptiest(double amount) {
        Iterator<Map.Entry<String, Double>> iterator = money.entrySet().iterator();
        Map.Entry<String, Double> emptiest = iterator.next();
        Map.Entry<String, Double> next;
        while (iterator.hasNext()) {
            next = iterator.next();
            if (emptiest.getValue() > next.getValue()) {
                emptiest = next;
            }
        }
        double sum = money.get(emptiest.getKey()) + amount;
        money.put(emptiest.getKey(), Double.valueOf(sum));
    }

    private void addEvenly(double amount) {
        double chunk = amount / money.size();
        double sum;
        for (String next : money.keySet() ) {
            sum = money.get(next) + chunk;
            money.put(next, Double.valueOf(sum));
        }
    }
}
