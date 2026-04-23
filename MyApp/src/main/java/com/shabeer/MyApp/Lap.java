package com.shabeer.MyApp;

import org.springframework.stereotype.Component;


@Component
public class Lap implements Computer{
    @Override
    public void compile() {
        System.out.println("compile in laptop");
    }
}
