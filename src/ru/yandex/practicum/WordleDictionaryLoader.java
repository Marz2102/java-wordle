package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {

    public static WordleDictionary loadWordleDictionary(String fileName, PrintWriter writer) throws EmptyDictionaryException {
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
            writer.write(TimeLog.getDateTime() + "Файла не существует");
        } catch (IOException e) {
            writer.write(TimeLog.getDateTime() + "Ошибка при чтении файла");
        }

        if (words.isEmpty()) {
            writer.write(TimeLog.getDateTime() + ": Ошибка загрузки словаря");
            throw new EmptyDictionaryException(); //Если словарь пуст, кидаем исключение в мейн
        }

        writer.println(TimeLog.getDateTime() + ": Словарь успешно загружен");
        return new WordleDictionary(words);
    }
}
