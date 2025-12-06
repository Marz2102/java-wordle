package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {

    public static WordleDictionary loadWordleDictionary(String fileName, Path logFile) throws IOException {
        List<String> words = new ArrayList<>();

        try (FileReader reader = new FileReader(fileName, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            while (bufferedReader.ready()) {
                String word = bufferedReader.readLine().toLowerCase().replaceAll("ё", "е");
                if (word.length() == 5) {
                    words.add(word);
                }
            }
        } catch (FileNotFoundException e) {
            Files.writeString(logFile, "Файла не существует");
        } catch (IOException e) {
            Files.writeString(logFile, "Ошибка при чтении файла");
        }

        Files.writeString(logFile, TimeLog.getDateTime() + ": Словарь успешно загружен", StandardCharsets.UTF_8);
        return new WordleDictionary(words);
    }
}
