package com.backend;

import com.InvalidFieldsNumException;
import com.ParseValueException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CSVParser {

    // Methods extended from assignment 1
    public static void readPosts(File file) throws FileNotFoundException {
        // create file instance
        System.out.printf("Reading '%s' file ...\n", file.getName());

        // create scanner for the file
        int expectedFieldsNum = 6;
        int readRowsCount = 0;
        int validRows = 0;
        Scanner scan = new Scanner(file);
        // skip first row (headers)
        if (scan.hasNextLine()) {
            scan.nextLine();
        }

        PostController postController = new PostController();
        // process each row
        while (scan.hasNextLine()) {
            try {
                readRowsCount++;
                String[] fields = parseCSV(scan.nextLine(), expectedFieldsNum);
                // instantiate post object and added to the collection
                int ID = CustomScanner.parseInt(fields[0], 0);
                if (postController.postIDisUnique(ID)) {
                    try {
                        // Skip if the there is an already existing post
                        String content = fields[1];
                        String author = CustomScanner.parseStr(fields[2]);
                        int likes = CustomScanner.parseInt(fields[3], 0);
                        int shares = CustomScanner.parseInt(fields[4], 0);
                        String dateTime = CustomScanner.parseDateTime(fields[5]);
                        // create post obj
                        postController.addNewPost(ID, content, likes, shares, dateTime);
                        validRows++;
                    } catch (ParseValueException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } catch (InvalidFieldsNumException e) {
                System.out.println("Expected " + e.getExpectedFieldNum() + " fields but got " +
                        e.getNumOfFields() + "!");
            } catch (ParseValueException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.printf("%d valid posts has been loaded\n", validRows);
        System.out.printf("%d invalid posts has been ignored\n", readRowsCount - validRows);
    }

    private static String[] parseCSV(String str, int expectedFieldsNum) throws InvalidFieldsNumException {
        String[] fields = str.split(",");

        if (fields.length != expectedFieldsNum) {
            throw new InvalidFieldsNumException(expectedFieldsNum, fields.length);
        }

        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].strip();
        }
        return fields;
    }

}
