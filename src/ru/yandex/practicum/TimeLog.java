package ru.yandex.practicum;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeLog {

    public static String getDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}
