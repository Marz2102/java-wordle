package ru.yandex.practicum;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;

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
            if (!Files.exists(logFile)) {
                Files.createFile(logFile);
            } else {
                Files.write(logFile, new byte[0]); // Очищаем файл логирования перед запуском игры
            }
        } catch (IOException e) {
            System.out.println("Ошибка создания файла логирования");
            return;
        }

        WordleDictionary wordleDictionary = WordleDictionaryLoader.loadWordleDictionary(fileName, logFile);
        WordleGame game = new WordleGame(wordleDictionary);
        Scanner scanner = new Scanner(System.in);

        Files.writeString(logFile, TimeLog.getDateTime() + ": Игра началась", StandardCharsets.UTF_8);

        while (true) {
            if (game.getSteps() == 0) {
                System.out.println("У вас закончились попытки");
                break;
            }

            System.out.println("У вас осталось " + game.getSteps() + " попыток, введите русское слово длины 5");
            String word = scanner.nextLine().toLowerCase().replaceAll("ё", "е").trim();

            if (!game.checkWord(word)) {
                System.out.println("Введено некорректное слово, попробуйте еще раз");
            } else {
                if (Objects.equals(word, game.getAnswer())) {
                    Files.writeString(logFile, TimeLog.getDateTime() + ": Игрок отгадал слово", StandardCharsets.UTF_8);
                    break;
                }

                if (word.isEmpty()) {
                    System.out.println(game.getHint());
                    continue;
                }

                game.handleNewWord(word);
            }

        }
    }
}
