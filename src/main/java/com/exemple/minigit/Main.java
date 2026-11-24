package com.exemple.minigit;

import com.exemple.minigit.commands.InitCommand;


public class Main {

    public static void main(String[] args) {

        InitCommand init = new InitCommand();
        String key = args[0];


        boolean initialized = true;

        switch (key) {
            case "init":
                initialized = init.execute();
                break;

        }

        System.out.println(initialized ? "Repositiry initialized" : "Repository already exists");


    }
}
