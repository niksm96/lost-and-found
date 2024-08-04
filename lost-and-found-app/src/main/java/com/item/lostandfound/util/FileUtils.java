package com.item.lostandfound.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.item.lostandfound.model.Item;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtils {

    public static File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = File.createTempFile("inputFileTmp", ".txt");
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    public static List<Item> listOfItemsFromFile(File file){
        Map<String, String> map = new HashMap<String, String>();
        List<Item> list = new ArrayList<Item>();
        BufferedReader br = null;

        try {
            // create BufferedReader object from the File
            br = new BufferedReader(new FileReader(file));

            String line = null;

            // read file line by line
            while ((line = br.readLine()) != null) {

                if(line.contains("------")){
                    Item item = new Item();
                    map.forEach((key, value) -> {
                        if(key.equals("ItemName"))
                            item.setItemName(value);
                        if(key.equals("Quantity"))
                            item.setQuantity(Integer.valueOf(value));
                        if(key.equals("Place"))
                            item.setPlace(value);
                    });
                    list.add(item);
                    continue;
                }

                // split the line by :
                String[] parts = line.split(":");

                // first part is key, second is value
                String key = parts[0].trim();
                String value = parts[1].trim();

                // put name, number in HashMap if they are
                // not empty
                if (!key.isEmpty() && !value.isEmpty()){
                    map.put(key, value);
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {

            // Always close the BufferedReader
            if (br != null) {
                try {
                    br.close();
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return list;
    }
}
