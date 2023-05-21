package com.tart.app;

import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class App {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Hello World!");

//        var PROPERTIES_FILENAME = "application.properties";
//
//        PropertiesConfiguration config = new PropertiesConfiguration();
//
//        try {
//            config.load(PROPERTIES_FILENAME);
//        } catch (ConfigurationException ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        var appVersion = config.getString("version");
//        System.out.println(appVersion);
        // Read properties
        Properties conf = PropertiesLoader.loadProperties();
        String property = conf.getProperty("version");
        System.out.println(property);

        // Open image
//        var r = Runtime.getRuntime();
//        var imgPath = "";
//        var cmdarray = new String[]{"eog", imgPath};
//        var p = r.exec(cmdarray);
//        p.waitFor();
        var FILE_TAGS = "tags.json";
        var TARGET_DIR = "/tmp/tart";
//
//        File file = new File(TARGET_DIR, FILE_TAGS);
//        var path = file.getPath();

        var path = Paths.get(TARGET_DIR, FILE_TAGS).toString();

        // Load tags
        var builder = new GsonBuilder();
        var gson = builder.create();
        var fileReader = new FileReader(path);
        var bufferedReader = new BufferedReader(fileReader);
        var tags = gson.fromJson(bufferedReader, Tag[].class);

        for (var tag : tags) {
            System.out.println(tag.getId() + " " + tag.getTitle());
        }

        // Save tags
        try (var fileWriter = new FileWriter(path)) {
            fileWriter.write(gson.toJson(tags));
        }
    }
}
