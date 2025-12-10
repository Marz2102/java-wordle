package ru.yandex.practicum;

import java.util.*;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {
    private final Random rand = new Random();
    private final List<String> words;
    private final Set<String> allWords = new HashSet<>();

    public WordleDictionary(List<String> words) {
        this.words = words;
        this.allWords.addAll(words);
    }

    public List<String> getWords() {
        return Collections.unmodifiableList(words);
    }

    public String getRandomWord() {
        return words.get(rand.nextInt(words.size()));
    }

    public boolean contains(String word) {
        return allWords.contains(word);
    }

    public String getWordByLetters(Map<Character, Integer> letters, List<Character> wordsPosition, Set<String> hints) {
        for (String word : words) {
            if (checkTotalLetters(letters, word) && checkLettersPosition(wordsPosition, word) && !hints.contains(word)) {
                hints.add(word);
                return word;
            }
        }
        return null;
    }

    private boolean checkLettersPosition(List<Character> wordsPosition, String word) {
        for (int i = 0; i < wordsPosition.size(); ++i) {
            if (wordsPosition.get(i) != null && wordsPosition.get(i) != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkTotalLetters(Map<Character, Integer> letters, String word) {
        Map<Character, Integer> newLetters = new HashMap<>();
        for (char c : word.toCharArray()) {
            newLetters.put(c, newLetters.getOrDefault(c, 0) + 1);
        }

        for (Character c : letters.keySet()) {
            if (newLetters.get(c) == null && letters.get(c) > 0) {
                return false;
            }
            if (letters.get(c) == 0 && newLetters.containsKey(c)) {
                return false;
            }
        }
        return true;
    }
}
