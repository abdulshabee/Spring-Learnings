package com.shabeer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        Dev dev = context.getBean(Dev.class);
        System.out.println("Role "+ dev.getRole());
        dev.build();
    }
}
