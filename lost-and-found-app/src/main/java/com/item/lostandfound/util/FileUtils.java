package com.item.lostandfound.util;

import com.item.lostandfound.exceptions.InvalidFileException;
import com.item.lostandfound.exceptions.NoFileUploadedException;
import com.item.lostandfound.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class that performs operations on or with files.
 */
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);


    public static void validateFile(MultipartFile multipartFile) throws NoFileUploadedException {
        if(multipartFile == null){
            throw new NoFileUploadedException("No file detected for upload. Please upload file");
        } else if (multipartFile.isEmpty()) {
            throw new InvalidFileException("File is empty. Please upload file with proper contents");
        }
    }
    /**
     * Converts multipart file to a readable file.
     * @param multipartFile
     * @return file
     * @throws IOException
     */
    public static File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = null;
        file = File.createTempFile("inputFileTmp", ".txt");
        multipartFile.transferTo(file);
        return file;
    }

    /**
     * Parses the file and extracts the list of lost items onto a List of Item object.
     * @param file
     * @return list of items
     * @throws InvalidFileException
     * @throws IOException
     */
    public static List<Item> listOfItemsFromFile(File file) throws InvalidFileException, IOException {
        Map<String, String> map = new HashMap<String, String>();
        List<Item> list = new ArrayList<Item>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            logger.error("Temporary file could not be found, file: {}", file.getPath(), e);
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
            if(!line.contains(":"))
                continue;
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
