package org.example.parsers;

import org.example.ParserWorker;


public class NewData<T> implements ParserWorker.OnNewDataHandler<T> {
    public void onNewData(Object sender, T args) {
        if (args instanceof Iterable) {
            Iterable<?> iterableArgs = (Iterable<?>) args;
            for (Object arg : iterableArgs) {
                System.out.println(arg);
            }
        } else {
            System.out.println(args);
        }
    }
}
