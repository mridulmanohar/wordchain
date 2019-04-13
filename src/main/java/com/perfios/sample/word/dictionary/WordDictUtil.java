package com.perfios.sample.word.dictionary;

import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordDictUtil {

    public static List<String> wordList;

    static {
        wordList = new ArrayList<>();
        loadWordListFromFile();
    }

    public static List<String> getWordList() {
        return wordList;
    }

    public static void loadWordListFromFile() {
        try {
            File folder = ResourceUtils.getFile("classpath:words");
            File[] filesArray = folder.listFiles();
            File wordListFile = null;
            for (File file : filesArray) {
                wordListFile = file;
            }

            String str = FileUtils.readFileToString(wordListFile, "UTF-8");
            String[] words = str.split("\\s+");
            wordList.addAll(Arrays.<String>asList(words));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
