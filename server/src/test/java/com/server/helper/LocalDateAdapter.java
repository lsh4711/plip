package com.server.helper;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

// Gson에서 LocalDate, LocalDateTime을 원하는 포맷으로 직렬화
public class LocalDateAdapter implements JsonSerializer<LocalDateTime> {
    @Override
    public JsonElement serialize(LocalDateTime date,
            Type typeOfSrc,
            JsonSerializationContext context) {
        return new JsonPrimitive(date
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)); // "yyyy-mm-dd"
    }
}
