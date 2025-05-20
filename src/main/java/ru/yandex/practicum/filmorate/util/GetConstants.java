package ru.yandex.practicum.filmorate.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GetConstants {
	public static final DateTimeFormatter FORMAT_LOCAL_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static final String COUNT = "10";
	public static final LocalDate THRESHOLD_DATE = LocalDate.of(1895, 12, 28);
}
