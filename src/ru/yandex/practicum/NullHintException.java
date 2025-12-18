package ru.yandex.practicum;

public class NullHintException extends Exception {

    public NullHintException() {
        super("Подсказки в словаре не нашлось, игра завершена");
    }
}
