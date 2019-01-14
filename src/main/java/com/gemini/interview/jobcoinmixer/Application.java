package com.gemini.interview.jobcoinmixer;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

@Getter
public class Application {

    private Queue<Jobcoin> network = new LinkedBlockingQueue<>();


    public static void main(String[] args) {
        Application application = new Application();
        application.go();
    }

    public void go() {

        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.println("To register please enter list of addresses comma delimited");
        input = scanner.nextLine();
        String uniqueaddress = JobcoinMixer.getInstance().register(Lists.newArrayList(input.split(",")));
        System.out.println("Your unique address is: " + uniqueaddress);
        Thread thread = new Thread(new JobcoinMixerRunnable(network));
        thread.start();
        System.out.println("To transfer jobcoin please enter fromaddress:toaddress:amount or 'stop' to stop");
        while (true) {
            scanner = new Scanner(System.in);
            input = scanner.nextLine();
            if (input.equals("stop")) {
                System.out.println("Here is your wallet: " + JobcoinMixer.getInstance().getWallet(uniqueaddress));
                System.exit(0);
            } else {
                Jobcoin jobcoin = validate(input);
                if (jobcoin == null) {
                    System.out.println("Double check your coin, it should be from:to:x.xx");
                } else {
                    network.add(jobcoin);
                }
            }
        }
    }

    private Jobcoin validate(String jobcoin) {
        if (Strings.isNullOrEmpty(jobcoin)) return null;
        String[] tokens = jobcoin.split(":");
        if (tokens.length < 3) return null;
        String from = tokens[0], to = tokens[1];
        double amount = Double.valueOf(tokens[2]);
        return new Jobcoin(from, to, amount);
    }
}
