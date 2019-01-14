package com.gemini.interview.jobcoinmixer;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Jobcoin {

    private String from;
    private String to;
    private double amount;
}
