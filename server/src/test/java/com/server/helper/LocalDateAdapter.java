package com.server.helper;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

// Gson에서 LocalDate, LocalDateTime을 원하는 포맷으로 직렬화
public class LocalDateAdapter implements JsonSerializer<LocalDate> {
    @Override
    public JsonElement serialize(LocalDate date,
        Type typeOfSrc,
        JsonSerializationContext context) {
        return new JsonPrimitive(date
            .format(DateTimeFormatter.ISO_LOCAL_DATE)); // "yyyy-mm-dd"
    }
}
