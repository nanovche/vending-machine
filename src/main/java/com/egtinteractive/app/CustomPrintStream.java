package com.egtinteractive.app;

public class CustomPrintStream {

  private static String message;

  public static void print(String msg){
      System.out.println(message);
      message = msg;
  }

  public static String getMessage() {
    return message;
  }

}
