package com.example.auth;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        System.out.println(PasswordUtil.hashPassword("AdminPass123!".toCharArray()));
    }
}
