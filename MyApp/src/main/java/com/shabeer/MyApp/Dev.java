package com.shabeer.MyApp;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class Dev {


    public Computer computer;

    public Dev(  @Qualifier("desktop") Computer computer){
        this.computer = computer;
    }
    public void build(){
        System.out.println("building the code");
        computer.compile();
    }
}
