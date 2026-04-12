package com.spring.core.DI;

import org.springframework.stereotype.Component;

@Component
public class ComponentClass {

    private final AutowiredClass autowiredClass;

    public ComponentClass(AutowiredClass autowiredClass) {
        this.autowiredClass = autowiredClass;
        System.out.println("Component Class Constructor");
    }

    static{
        System.out.println("Component Class Static Block");
    }

    public void display(){
        System.out.println("Component Class Display Method");
        autowiredClass.display();
    }
}
