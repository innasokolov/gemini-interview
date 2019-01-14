package com.gemini.interview.jobcoinmixer;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class JobcoinMixerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @After
    public void teardown() {
        JobcoinMixer.reset();
    }

    @Test
    public void testRegisterSuccess() {
        Assert.assertTrue(JobcoinMixer.getInstance().getUsedLaundryAddresses().isEmpty());
        JobcoinMixer.getInstance().register(Lists.newArrayList("Inna", "Brigid"));
        Assert.assertEquals(1, JobcoinMixer.getInstance().getUsedLaundryAddresses().size());
        Assert.assertEquals("Claptrap", JobcoinMixer.getInstance().getUsedLaundryAddresses().get(0));
    }

    @Test
    public void testRegisterNoAvailableAddresses() {
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Unable to register, there are no available addresses.");
        Assert.assertTrue(JobcoinMixer.getInstance().getUsedLaundryAddresses().isEmpty());
        JobcoinMixer.getInstance().register(Lists.newArrayList("Inna1"));
        JobcoinMixer.getInstance().register(Lists.newArrayList("Inna2"));
        JobcoinMixer.getInstance().register(Lists.newArrayList("Inna3"));
        JobcoinMixer.getInstance().register(Lists.newArrayList("Inna4"));
        //we have only four
        JobcoinMixer.getInstance().register(Lists.newArrayList("Inna5"));
    }

    @Test
    public void testLaunder() {
        Assert.assertTrue(JobcoinMixer.getInstance().getUsedLaundryAddresses().isEmpty());
        String inna = JobcoinMixer.getInstance().register(Lists.newArrayList("Inna"));
        String brigid = JobcoinMixer.getInstance().register(Lists.newArrayList("Brigid"));
        String john = JobcoinMixer.getInstance().register(Lists.newArrayList("John"));
        Assert.assertEquals(3, JobcoinMixer.getInstance().getUsedLaundryAddresses().size());
        Assert.assertTrue(JobcoinMixer.getInstance().launder(new Jobcoin("Danny", "Wilhelm", 5.5)));
        Assert.assertTrue(JobcoinMixer.getInstance().launder(new Jobcoin("Danny", "Claptrap", 6.5)));
        Assert.assertTrue(JobcoinMixer.getInstance().launder(new Jobcoin("Danny", "Jack", 7.5)));
        Assert.assertEquals(5.5, JobcoinMixer.getInstance().getWallet(john).get(), 0.009);
        Assert.assertEquals(6.5, JobcoinMixer.getInstance().getWallet(inna).get(), 0.009);
        Assert.assertEquals(7.5, JobcoinMixer.getInstance().getWallet(brigid).get(), 0.009);
        Assert.assertFalse(JobcoinMixer.getInstance().launder(new Jobcoin("Danny", "Nurse Nina", 6.6)));
    }

    @Test
    public void testGetAvailableLaundryAddresses() {
        Assert.assertEquals(4, JobcoinMixer.getInstance().getAvailableLaundryAddresses().size());
    }

    @Test
    public void testGetTransactionLog() {
        JobcoinMixer.getInstance().register(Lists.newArrayList("Inna", "Brigid"));
        Assert.assertTrue(JobcoinMixer.getInstance().launder(new Jobcoin("Danny", "Claptrap", 6.5)));
        Assert.assertEquals(1, JobcoinMixer.getInstance().getTransactionLog().size());
        Assert.assertEquals(6.5, JobcoinMixer.getInstance().getTransactionLog().get(0).getAmount(), 0.009);
    }
}
