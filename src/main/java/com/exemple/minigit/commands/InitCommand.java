package com.exemple.minigit.commands;


import com.exemple.minigit.Repository.Repository;

import java.io.IOException;

public class InitCommand  implements Command {

    public boolean execute() {

        try{
            Repository repo = new Repository();
            return repo.initialize();

        }catch(IOException io){
            System.out.println(io.getMessage());
        }

        return false;
    }



}
