package com.example.anagram.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Anagram {

    private String theWord;
    private List<String> theAnagrams = new ArrayList<>();

    public Anagram(String theWord, List<String> potentialAnagrams) {
        this.theWord = theWord;
        this.findAnagrams(potentialAnagrams);
    }

    private void findAnagrams(List<String> aList) {
        String sortedWord = sortWord(this.theWord);
        for (String string: aList) {
            if (!theWord.equals(string) && sortedWord.equals(this.sortWord(string))) {
                this.theAnagrams.add(string);
            }
        }
    }

    private String sortWord(String aString) {
        char [] anArray = aString.toLowerCase().toCharArray();
        Arrays.sort(anArray);
        return new String(anArray);
    }

    public String getTheWord() {
        return this.theWord;
    }

    public List<String> getTheAnagrams() {
        return new ArrayList<>(this.theAnagrams);
    }
}
