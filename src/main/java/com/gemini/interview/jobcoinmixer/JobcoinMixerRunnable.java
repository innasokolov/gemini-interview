package com.gemini.interview.jobcoinmixer;

import java.util.Queue;

public class JobcoinMixerRunnable implements Runnable {

    private Queue<Jobcoin> network;

    public JobcoinMixerRunnable(Queue<Jobcoin> network) {
       this.network = network;
    }

    @Override
    public void run() {
        while(true) {
            Jobcoin jobcoin = network.poll();
            if (jobcoin != null) {
                System.out.println(JobcoinMixer.getInstance().launder(jobcoin)?"************ yay transaction":"*********** nay transaction");
            }
        }

    }
}
