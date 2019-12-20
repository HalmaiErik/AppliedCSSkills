/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private int wordLength = DEFAULT_WORD_LENGTH;
    private Random random = new Random();
    private ArrayList<String> wordList = new ArrayList<>();
    private HashSet<String> wordSet = new HashSet<>();
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();
    private HashMap<Integer, ArrayList<String>> sizeToWords = new HashMap<>();

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            String key = sortLetters(word);
            int length = word.length();
            wordList.add(word);
            wordSet.add(word);
            if(sizeToWords.containsKey(length))
                sizeToWords.get(length).add(word);
            else {
                ArrayList<String> newLengthList = new ArrayList<>();
                newLengthList.add(word);
                sizeToWords.put(length, newLengthList);
            }
            if(lettersToWord.containsKey(key))
                lettersToWord.get(key).add(word);
            else {
                ArrayList<String> newLetterList = new ArrayList<>();
                newLetterList.add(word);
                lettersToWord.put(key, newLetterList);
            }
        }
    }

    public boolean isGoodWord(String word, String base) {
        return wordSet.contains(word) && !word.contains(base);
    }

    private String sortLetters(String word) {
        char[] tempArr = word.toCharArray();
        Arrays.sort(tempArr);
        return new String(tempArr);
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
       /* for(String word : wordList) {
            if(sortLetters(targetWord).equals(sortLetters(word)))
                result.add(word);
        }
        */
       result = lettersToWord.get(sortLetters(targetWord));
       result.remove(targetWord);
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = getAnagrams(word);
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        for(char c : chars) {
            String sortedWord = sortLetters(word + c);
            result.add(sortedWord);
        }
        return result;
    }

    public String pickGoodStarterWord() {
        String goodStarterWord;
        ArrayList<String> list = sizeToWords.get(wordLength);
        int initialIndex = random.nextInt(list.size());
        int index = initialIndex;
        while(true) {
            String word = list.get(index);
            String key = sortLetters(word);
            if (lettersToWord.get(key).size() >= MIN_NUM_ANAGRAMS) {
                goodStarterWord = word;
                break;
            }
            index = (index + 1)%list.size();
            if(index == initialIndex) {
                wordLength++;
                list = sizeToWords.get(wordLength);
                initialIndex = random.nextInt(list.size());
                index = initialIndex;
            }
        }
        if(wordLength < MAX_WORD_LENGTH)
            wordLength++;
        return goodStarterWord;

        // return "stop";
    }

    private int getAnagramsCount(String s) {
        int count = 0;
        for(String word : wordList) {
            if(sortLetters(word).equals((sortLetters(s))))
                count += 1;
        }
        return count;
    }
}
