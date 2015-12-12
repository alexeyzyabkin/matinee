package com.letionik.matinee.converter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
public interface Converter<T, K> {
    T convertTo(K obj);

    default List<T> convertCollection(List<K> obj) {
        List<T> result = new ArrayList<>();
        for (K k : obj) {
            result.add(convertTo(k));
        }
        return result;
    }
}
