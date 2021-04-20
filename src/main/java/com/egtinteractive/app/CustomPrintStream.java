package com.egtinteractive.app;

import java.util.List;

public class CustomPrintStream {

    private static List<String> messages;
    private static int index = 0;

    public static void print(String message){
        System.out.println(message);
        messages.add(message);
    }

    public static String getMessage() {
        return messages.get(index++);
    }

}
