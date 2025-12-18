package ru.yandex.practicum;

public class WordNotFoundInDictionary extends Exception {

    public WordNotFoundInDictionary() {
        super("Word not found in dictionary");
    }
}
