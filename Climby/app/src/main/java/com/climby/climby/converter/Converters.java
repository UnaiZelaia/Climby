package com.climby.climby.converter;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class Converters {
    static DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
            .appendFraction(ChronoField.MILLI_OF_SECOND, 1, 9, true) // minimum 1 digit, maximum 9 digits, optional
            .toFormatter();

    @TypeConverter
    public static String localDateTimeStr(LocalDateTime date){
        return date == null ? null : date.toString();
    }

    @TypeConverter
    public static LocalDateTime strLocalDateTime(String date){
        return LocalDateTime.parse(date, formatter);
    }
}