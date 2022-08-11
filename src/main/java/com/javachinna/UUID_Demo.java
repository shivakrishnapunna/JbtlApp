/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javachinna;

import java.util.*;

public class UUID_Demo {

    public static void main(String[] args) {

        // Creating two UUIDs
        UUID uuid=UUID.randomUUID(); //Generates random UUID  
        String uuidd=uuid.toString();
        System.out.println("uuid : "+uuid+ " "+uuidd);
    }
}
