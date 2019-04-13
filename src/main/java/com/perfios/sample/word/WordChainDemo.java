package com.perfios.sample.word;

import com.perfios.sample.word.dictionary.WordDictUtil;
import com.perfios.sample.word.graph.WordGraph;

import java.util.List;
import java.util.Scanner;

public class WordChainDemo {

    public static List<String> wordList = WordDictUtil.getWordList();

    public static boolean validateInput(String s1, String s2) {
        if(s1.length() != s2.length())
            return false;

        boolean isFirstStrValid = wordList.contains(s1);
        boolean isSecondStrValid = wordList.contains(s2);

        return isFirstStrValid && isSecondStrValid;
    }

    public static void formWordChain(WordGraph graph, String s1, String s2) {
        List<String> paths = graph.shortestPath(s1, s2);
        if(paths != null)
        {
            System.out.println("Word-Chain for source: "+s1 +" | target: "+s2);
            paths.stream().forEach(a -> System.out.println(a));
        }
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter first word: ");
        String s1 = in.next();
        System.out.println("Enter second word: ");
        String s2 = in.next();

        if(!validateInput(s1,s2))
            System.out.println("Not Possible");
        else {
            WordGraph graph = new WordGraph();
            graph.loadWordGraph(wordList);
            formWordChain(graph, s1, s2);
        }
    }

}
