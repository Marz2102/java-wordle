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
    private final Map<Character, Integer> letters = new LinkedHashMap<>(); //мапа с кол-вом повторений букв
    private final List<Character> wordsPosition = new ArrayList<>(Collections.nCopies(5, null)); //массив, запоминающий верно угаданные позиции

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

    public boolean checkWord(String word) {
        for (int i = 0; i < word.length(); i++) {
            if (!(word.charAt(i) >= 'a' && word.charAt(i) <= 'я')) {
                return false;
            }
        }
        return (word.length() == answer.length() || word.isEmpty());
    }

    public String getHint() {
        --steps;
        if (letters.isEmpty()) {
            return dictionary.getRandomWord();
        }
        return dictionary.getWordByLetters(letters, wordsPosition);
    }

    public String handleNewWord(String word) {
        String out = "";
    }

}
