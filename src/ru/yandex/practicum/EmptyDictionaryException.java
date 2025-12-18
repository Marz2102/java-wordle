package ru.yandex.practicum;

public class EmptyDictionaryException extends Exception {

    public EmptyDictionaryException() {
        super("Словарь пуст, игра завершена");
        }
}
