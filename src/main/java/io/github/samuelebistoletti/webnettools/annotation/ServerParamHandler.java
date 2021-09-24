package io.github.samuelebistoletti.webnettools.annotation;

import io.github.samuelebistoletti.webnettools.payload.Payload;

import java.lang.reflect.Field;
import java.util.Collection;

public interface ServerParamHandler {
    Collection<String> handle(Field field, Payload payload);
}