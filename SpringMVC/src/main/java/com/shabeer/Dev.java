package com.shabeer;


public class Dev {

    private String role;

    private Computer com;

    public Dev(Computer com){
        System.out.println("Dev constructor");
        this.com = com;
    }
    public void build(){
        com.compile();
        System.out.println("Building a awesome project!!!");
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
