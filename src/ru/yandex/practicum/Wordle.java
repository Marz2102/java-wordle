package ru.yandex.practicum;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {
    private static Path logFile;
    private static String fileName = "words_ru.txt";

    public static void main(String[] args) throws IOException {
        try {
            logFile = Paths.get("logFile");
            Files.createFile(logFile);
        } catch (IOException e) {
            System.out.println("Ошибка создания файла логирования");
            return;
        }

        WordleDictionary wordleDictionary = WordleDictionaryLoader.loadWordleDictionary(fileName, logFile);


    }

}
