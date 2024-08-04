package com.item.lostandfound.util;

import com.item.lostandfound.exceptions.InvalidFileException;
import com.item.lostandfound.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = null;
        file = File.createTempFile("inputFileTmp", ".txt");
        multipartFile.transferTo(file);
        return file;
    }

    public static List<Item> listOfItemsFromFile(File file) throws InvalidFileException, IOException {
        Map<String, String> map = new HashMap<String, String>();
        List<Item> list = new ArrayList<Item>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            logger.error("File could not be found", e);
            throw new InvalidFileException(e.getMessage());
        }
        String line = null;

        while ((line = br.readLine()) != null) {

            if (line.contains("------")) {
                Item item = new Item();
                map.forEach((key, value) -> {
                    if (key.equals("ItemName"))
                        item.setItemName(value);
                    if (key.equals("Quantity"))
                        item.setQuantity(Integer.valueOf(value));
                    if (key.equals("Place"))
                        item.setPlace(value);
                });
                list.add(item);
                continue;
            }
            String[] parts = line.split(":");

            String key = parts[0].trim();
            String value = parts[1].trim();

            if (!key.isEmpty() && !value.isEmpty()) {
                map.put(key, value);
            }
        }
        br.close();
        return list;
    }
}
