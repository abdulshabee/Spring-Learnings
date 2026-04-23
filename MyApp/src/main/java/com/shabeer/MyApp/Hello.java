package com.shabeer.MyApp;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class Hello {

    @GetMapping
    public String greet(){
        return "Hello world";
    }
}
