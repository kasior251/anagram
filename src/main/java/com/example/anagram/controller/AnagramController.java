package com.example.anagram.controller;

import com.example.anagram.model.Anagram;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AnagramController {

    private static final String FILE = "static/ordbok.txt";

    @GetMapping("/anagrams")
    public List<Anagram> getAnagrams() {
        List<String> list;
        try {
            list = this.readFile();
        }
        catch (IOException e) {
            return null;
        }
        List<Anagram> anagrams = new ArrayList<>();

        Long start = System.currentTimeMillis();

        for (String s: list) {
            Anagram anagram = new Anagram(s, new ArrayList<>(list));
            if (!anagram.getTheAnagrams().isEmpty()) {
                anagrams.add(anagram);
            }
        }

        Long stop = System.currentTimeMillis();
        System.out.println("It took " + (stop - start) + " miliseconds to compute");

        return anagrams;
    }

    private List<String> readFile() throws  IOException {
        List<String> aList = new ArrayList<>();
        try {
            InputStream file = this.getClass().getClassLoader().getResourceAsStream(FILE);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(file))) {
                while (br.ready()) {
                    String line = br.readLine().replaceAll("\\s+","");
                    if (line.length() == 0) {
                        continue;
                    }
                    aList.add(line);
                }
            }
        } catch (Exception e) {
            throw new IOException(e);
        }
        return aList;
    }

}
