package com.gemini.interview.jobcoinmixer;

import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class WalletTest {

    private Wallet wallet;

    @Test
    public void testAddToEmptiest() {
        Map<String, Double> money = Maps.newHashMap();
        money.put("Inna", 0.0);
        money.put("Brigid", 3.4);
        wallet = new Wallet(money);

        Assert.assertEquals(0.0, wallet.get("Inna").doubleValue(), 0.009);
        Assert.assertEquals(3.4, wallet.get("Brigid").doubleValue(), 0.009);

        wallet.add(5.4, Wallet.DistributionType.TO_EMPTIEST);

        Assert.assertEquals(5.4, wallet.get("Inna").doubleValue(), 0.009);
        Assert.assertEquals(3.4, wallet.get("Brigid").doubleValue(), 0.009);

        wallet.add(1.7, Wallet.DistributionType.TO_EMPTIEST);

        Assert.assertEquals(5.4, wallet.get("Inna").doubleValue(), 0.009);
        Assert.assertEquals(5.1, wallet.get("Brigid").doubleValue(), 0.009);
    }

    @Test
    public void testAddEvenly() {
        Map<String, Double> money = Maps.newHashMap();
        money.put("Seeta", 0.0);
        money.put("Geeta", 3.4);
        wallet = new Wallet(money);

        Assert.assertEquals(0.0, wallet.get("Seeta").doubleValue(), 0.009);
        Assert.assertEquals(3.4, wallet.get("Geeta").doubleValue(), 0.009);

        wallet.add(6.0, Wallet.DistributionType.EVENLY);

        Assert.assertEquals(3.0, wallet.get("Seeta").doubleValue(), 0.009);
        Assert.assertEquals(6.4, wallet.get("Geeta").doubleValue(), 0.009);

    }

    @Test
    public void testGetForAddress()  {
        Map<String, Double> money = Maps.newHashMap();
        money.put("Inna", 2.8);
        money.put("Brigid", 3.4);
        wallet = new Wallet(money);

        Assert.assertEquals(2.8, wallet.get("Inna").doubleValue(), 0.009);
        Assert.assertEquals(3.4, wallet.get("Brigid").doubleValue(), 0.009);
        Assert.assertNull(wallet.get("Danny"));
    }

    @Test
    public void testGetAll() {
        Map<String, Double> money = Maps.newHashMap();
        money.put("Inna", 2.8);
        money.put("Brigid", 3.4);
        wallet = new Wallet(money);

        Assert.assertEquals(6.2, wallet.get().doubleValue(), 0.009);
    }
}
