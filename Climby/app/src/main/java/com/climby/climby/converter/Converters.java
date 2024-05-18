package com.climby.climby.converter;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Converters {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @TypeConverter
    public static String localDateTimeStr(LocalDateTime date){
        return date == null ? null : date.toString();
    }

    @TypeConverter
    public static LocalDateTime strLocalDateTime(String date){
        return LocalDateTime.parse(date, formatter);
    }
}