package com.exemple.minigit.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class Repository {


    private final ArrayList<String> folders = new ArrayList<>(List.of("objects", "commits"));
    private final ArrayList<String> files = new ArrayList<>(List.of( "index", "HEAD", "config"));



    public boolean initialize() throws IOException {

        File mainFolder = new File(".minigit");

        if (mainFolder.exists()) {
            return false;
        }

        try {

            if (!mainFolder.mkdir()) {
                return false;
            }

            for (String folder : folders) {
                File directory = new File(mainFolder, folder);

                if (!directory.mkdir()) {
                    delete(mainFolder);
                    return false;
                }
            }

            for (String f : files) {
                File file = new File(mainFolder, f);

                if (!file.createNewFile()) {
                    delete(mainFolder);
                    return false;
                }
            }


            createConfig(".minigit/config");


            return true;

        }catch (IOException e) {
            delete(mainFolder);
            System.out.println("Initialization failed: " + e.getMessage());
            return false;
        }
    }


    public static void delete(File entry) {

        try {
            if (entry.isFile()) {
                entry.delete();
            } else {
                String[] entries = entry.list();
                for (String e : entries) {
                    File currentFile = new File(entry.getPath(), e);
                    currentFile.delete();
                }
                entry.delete();
            }
        } catch (Exception e) {
            System.out.println("Deleting failed: " + e.getMessage());
        }

    }

    public static void createConfig(String configPath) throws IOException {

        String configContent = """
                [repository]
                name = MiniGit
                    
                [core]
                hashAlgorithm = SHA-256
                \n
                """;

        Files.writeString(Path.of(configPath), configContent);
    }


}
