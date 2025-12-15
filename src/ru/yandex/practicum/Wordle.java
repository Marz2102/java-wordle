package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
    private static String logFile = "logFile.txt";
    private static final String fileName = "words_ru.txt";

    public static void main(String[] args) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFile, false))) {

            WordleDictionary wordleDictionary = WordleDictionaryLoader.loadWordleDictionary(fileName, writer);
            WordleGame game = new WordleGame(wordleDictionary);
            Scanner scanner = new Scanner(System.in);

            writer.println(TimeLog.getDateTime() + ": Игра началась. Попыток осталось: " + game.getSteps());

            while (true) {
                if (game.getSteps() == 0) {
                    System.out.println("У вас закончились попытки. Правильный ответ: " + game.getAnswer());
                    writer.println(TimeLog.getDateTime() + ": Правильный ответ: " + game.getAnswer());
                    break;
                }

                String word = scanner.nextLine().toLowerCase().replaceAll("ё", "е").trim();

                try {
                    game.checkWord(word);
                } catch (WordNotFoundInDictionary e) {
                    writer.println(TimeLog.getDateTime() + ": " + e.getMessage() + ". Попыток осталось: " + game.getSteps());
                    System.out.println(e.getMessage());
                    continue;
                }

                if (Objects.equals(word, game.getAnswer())) {
                    writer.println(TimeLog.getDateTime() + ": Игрок отгадал слово: " + word);
                    System.out.println("Вы выиграли, правильный ответ: " + word);
                    break;
                }

                if (word.isEmpty()) {
                    String hint = game.getHint();
                    if (hint == null) {
                        writer.println(TimeLog.getDateTime() + ": Ошибка во время поиска подсказки");
                        throw new NullHintException();
                    }

                    System.out.println(hint);
                    writer.println(TimeLog.getDateTime() + ": Игрок запросил подсказку. Попыток осталось: " + game.getSteps());
                    continue;
                }

                String out = game.handleNewWord(word);
                System.out.println(out);
                writer.println(TimeLog.getDateTime() + ": Игрок предложил вариант " + word + ". Попыток осталось: " + game.getSteps());
            }
        } catch (IOException e) {
            System.out.println("Ошибка создания файла логирования");
        } catch (EmptyDictionaryException | NullHintException e) {
            System.out.println(e.getMessage());
        }
    }
}
