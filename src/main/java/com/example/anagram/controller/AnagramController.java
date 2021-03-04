package com.example.anagram.controller;

import com.example.anagram.model.Anagram;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class AnagramController {

    private static final String FILE = "static/ordbok.txt";

    @GetMapping("/anagrams")
    public List<Anagram> getAnagrams() {
        List<String> list;
        try {
            list = this.readFile();
            List<Set<String>> secondList = this.readFile2();
            System.out.println(secondList);
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

    private static String sortLetters(String s) {
        return Stream.of(s.split("")).sorted().collect(Collectors.joining());
    }

    private List<Set<String>> readFile2() throws IOException {
        long start = System.currentTimeMillis();
        try (InputStream file = this.getClass().getClassLoader().getResourceAsStream(FILE)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));
            Stream<String> lines =  reader.lines();

            Collector<String, ?, Map<String, Set<String>>> collector = Collectors.groupingBy(AnagramController::sortLetters, Collectors.toSet());
            Map<String, Set<String>> anagrams = lines.collect(collector);

            List<Set<String>> anagramList = anagrams.values().stream().filter(list -> list.size() > 1).collect(Collectors.toList());
            System.out.println("It took " + (System.currentTimeMillis() - start) + " miliseconds to compute using Stream");
            return anagramList;
        }

    }


}
