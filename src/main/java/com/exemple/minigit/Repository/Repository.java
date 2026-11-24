package com.exemple.minigit.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;


public class Repository {


    private final List<String> folders = List.of("objects", "refs");
    private final List<String> files = List.of( "index", "HEAD", "config");



    public boolean initialize() {

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
                    delete();
                    return false;
                }else{
                    if(folder.equals("refs")){
                        File heads = new File(directory, "heads");
                        if (!heads.mkdir()) {
                            delete();
                            return false;
                        }
                        File main = new File(heads, "main");
                        if (!main.createNewFile()) {
                            delete();
                            return false;
                        }
                        }
                }

            }

            for (String f : files) {
                File file = new File(mainFolder, f);
                if (!file.createNewFile()) {
                    delete();
                    return false;
                }
            }

            File config = new File(mainFolder, "config");
            File head = new File(mainFolder, "HEAD");

            createConfig(config.getPath());
            createHead(head.getPath());

            return true;

        }catch (IOException e) {
            delete();
            System.out.println("Initialization failed: " + e.getMessage());
            return false;
        }
    }


    public static void delete() {

        Path path = Path.of(".minigit");

        try (var paths = Files.walk(path)) {
            for (Path current : paths.sorted(Comparator.reverseOrder()).toList()) {
                Files.delete(current);
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


    public static void createHead(String headPath) throws IOException {
        String headContent = "ref: refs/heads/main";
        Files.writeString(Path.of(headPath), headContent);
    }


}
