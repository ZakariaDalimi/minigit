package com.exemple.minigit.commands;


import com.exemple.minigit.Repository.Repository;

import java.io.IOException;

public class InitCommand  implements Command {

    public boolean execute() {

        Repository repo = new Repository();
        return repo.initialize();

    }



}
