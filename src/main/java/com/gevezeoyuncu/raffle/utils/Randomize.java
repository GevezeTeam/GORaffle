package com.gevezeoyuncu.raffle.utils;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Randomize<T> {

    private List<T> list;

    private Randomize(List<T> list) {
        this.list = list;
    }

    public static <T> Randomize create(List<T> list) {
        return new Randomize<T>(list);
    }

    @Nullable
    public T get() {
        if (list.size() < 1) return null;
        int randomElementIndex = ThreadLocalRandom.current().nextInt(list.size()) % list.size();
        return list.remove(randomElementIndex);
    }

    public Randomize<T> shuffle() {
        Collections.shuffle(list);
        return this;
    }

    public List<T> getList() {
        return list;
    }
}
