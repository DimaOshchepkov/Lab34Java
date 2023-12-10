package org.example.parsers;

import org.example.ParserWorker;

import lombok.val;

public class NewData<T extends Iterable<?>> implements ParserWorker.OnNewDataHandler<T> {
    public void onNewData(Object sender, T args) {
            for (val arg : args) {
                System.out.println(arg);
            }
    }
}
