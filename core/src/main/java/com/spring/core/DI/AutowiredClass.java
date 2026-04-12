package com.spring.core.DI;

import org.springframework.stereotype.Component;

@Component
public class AutowiredClass {

        static{
            System.out.println("Autowired Class Static Block");
        }

        public AutowiredClass() {
            System.out.println("Autowired Class Constructor");
        }


        public void display(){
            System.out.println("Autowired Class Display Method");
        }

}
