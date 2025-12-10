package ru.yandex.practicum;

import java.util.*;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {
    private final String answer;
    private int steps = 6;
    private final WordleDictionary dictionary;
    private final Map<Character, Integer> letters = new LinkedHashMap<>(); //мапа с присутствующими буквами в слове
    private final List<Character> wordsPosition = new ArrayList<>(Collections.nCopies(5, null)); //массив, запоминающий верно угаданные позиции
    private final Set<String> hints = new HashSet<>();

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
        this.answer = dictionary.getRandomWord(); //В качестве ответа берем рандомный элемент словаря
    }

    public int getSteps() {
        return steps;
    }

    public String getAnswer() {
        return answer;
    }

    public void checkWord(String word) throws WordNotFoundInDictionary {
        if (word.isEmpty()) {
            return;
        }

        if (!dictionary.contains(word)) {
            throw new WordNotFoundInDictionary();
        }

        for (int i = 0; i < word.length(); i++) {
            if (!(word.charAt(i) >= 'a' && word.charAt(i) <= 'я')) {
                throw new WordNotFoundInDictionary();
            }
        }
        if (word.length() != answer.length()) {
            throw new WordNotFoundInDictionary();
        }
    }

    public String getHint() {
        decrementSteps();
        if (letters.isEmpty()) {
            return dictionary.getRandomWord();
        }
        return dictionary.getWordByLetters(letters, wordsPosition, hints);
    }

    public String handleNewWord(String word) {
        decrementSteps();
        updateWordsPosition(word);
        updateLettersMap(word);

        StringBuilder out = new StringBuilder();
        for (int i = 0; i < word.length(); ++i) {
            if (word.charAt(i) == answer.charAt(i)) {
                out.append("+");
            } else if (answer.indexOf(word.charAt(i)) == -1) {
                out.append("-");
            } else {
                out.append("^");
            }
        }

        return out.toString();
    }

    private void updateWordsPosition(String word) {
        for (int i = 0; i < word.length(); ++i) {
            if (word.charAt(i) == answer.charAt(i)) {
                wordsPosition.set(i, word.charAt(i));
            }
        }
    }

    private void updateLettersMap(String word) {
        for (int i = 0; i < word.length(); ++i) {
            if (answer.indexOf(word.charAt(i)) != -1) {
                letters.put(word.charAt(i), 1);
            } else {
                letters.put(word.charAt(i), 0);
            }
        }
    }

    private void decrementSteps() {
        --steps;
    }

}
