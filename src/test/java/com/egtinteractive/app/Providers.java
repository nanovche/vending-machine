package com.egtinteractive.app;

import static com.egtinteractive.app.VendingMachine.INITIAL_QUANTITY_FOR_ITEM;
import static com.egtinteractive.app.VendingMachine.NUM_OF_ITEMS;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import org.testng.annotations.DataProvider;

public class Providers {

  @DataProvider(name = "providerForInSufficientAmountZero")
  public Object[][] providerForInSufficientAmountZero(){

    final VendingMachine vendingMachine = new VendingMachine();
    final float amount = 0;

    return new Object[][]{{vendingMachine, amount}};
  }

  @DataProvider(name = "providerForInSufficientAmountNegative")
  public Object[][] providerForInSufficientAmountNegative(){

    final VendingMachine vendingMachine = new VendingMachine();
    float amount;

    do {
      amount = new Random().nextInt();
    }while (amount >= 0 );

    return new Object[][]{{vendingMachine, amount}};
  }

  @DataProvider(name = "providerForAddCurrencySysOutRedirect")
  public Object[][] providerForAddCurrencySysOutRedirect(){

    PrintStream originalSystemOut;
    originalSystemOut = System.out;

    ByteArrayOutputStream systemOutContent;
    systemOutContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(systemOutContent));

    final VendingMachine vendingMachine = new VendingMachine();
    float amount;

    do {
      amount = new Random().nextFloat();
    }while (amount <= 0 );

    return new Object[][]{{vendingMachine, (float)Math.round(amount*100.0)/100.0, systemOutContent, originalSystemOut}};
  }

  @DataProvider(name = "providerForAddCurrency")
  public Object[][] providerForAddCurrency(){

    final VendingMachine vendingMachine = new VendingMachine();
    float amount;

    do {
      amount = new Random().nextFloat();
    }while (amount <= 0 );

    return new Object[][]{{vendingMachine, Math.round(amount*100.0)/100.0}};
  }

  @DataProvider(name = "providerForMessage")
  public Object[][] providerForMessage(){

    PrintStream originalSystemOut;
    originalSystemOut = System.out;

    ByteArrayOutputStream systemOutContent;
    systemOutContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(systemOutContent));

    final VendingMachine vendingMachine = new VendingMachine();

    return new Object[][]{{vendingMachine, systemOutContent, originalSystemOut}};
  }

  @DataProvider(name = "providerForReturnMoney")
  public Object[][] providerForReturnMoney(){

    PrintStream originalSystemOut;
    originalSystemOut = System.out;

    ByteArrayOutputStream systemOutContent;
    systemOutContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(systemOutContent));

    final float amount = (float)ThreadLocalRandom.current().nextDouble(0.40, 5.00);
    final VendingMachine vendingMachine = new VendingMachine();

    return new Object[][]{{vendingMachine, systemOutContent, originalSystemOut, amount}};
  }

  @DataProvider(name = "providerForReturnMoneyChange")
  public Object[][] providerForReturnMoneyChange()throws IOException {

    GetPropertyValues properties = new GetPropertyValues();
    properties.getPropValues();

    final int index = new Random().nextInt(Items.values().length);
    final float profit = Items.values()[index].getPrice();
    final float amount = (float)ThreadLocalRandom.current().nextDouble(profit+0.1, 5.00);

    final VendingMachine vendingMachine = new VendingMachine();

    return new Object[][]{{vendingMachine, amount, profit}};
  }

  @DataProvider(name = "providerForReturnMoneyNoChange")
  public Object[][] providerForReturnMoneyNoChange()throws IOException {

    GetPropertyValues properties = new GetPropertyValues();
    properties.getPropValues();

    final int index = new Random().nextInt(Items.values().length);
    final float amount = Items.values()[index].getPrice();

    final VendingMachine vendingMachine = new VendingMachine();

    return new Object[][]{{vendingMachine, amount}};
  }

  @DataProvider(name = "providerForHasCurrencySelectItem")
  public Object[][] providerForHasCurrencySelectItem() throws IOException {

    GetPropertyValues getPropertyValues = new GetPropertyValues();
    getPropertyValues.getPropValues();

    final VendingMachine vendingMachine = new VendingMachine();

    int numOfDrinks;
    do{
      numOfDrinks = new Random().nextInt(NUM_OF_ITEMS*INITIAL_QUANTITY_FOR_ITEM + 1);
    }while(numOfDrinks == 0);

    final StringBuilder input = new StringBuilder();
    float insertedAmount = 0.0f;
    for (int i = 0; i < numOfDrinks; i++) {
      final Items item = Items.values()[new Random().nextInt(Items.values().length)];
      input.append(item.getName()).append("\nY\n");
      insertedAmount = insertedAmount + item.getPrice();
    }

    final String finalInput  = input.substring(0, input.length() - 2) + "N\n";

    InputStream sysInBackup = System.in;
    ByteArrayInputStream sysInNew = new ByteArrayInputStream(finalInput.getBytes());
    System.setIn(sysInNew);

    PrintStream sysOutBackup;
    sysOutBackup = System.out;
    ByteArrayOutputStream sysOutNew;
    sysOutNew = new ByteArrayOutputStream();
    System.setOut(new PrintStream(sysOutNew));

    vendingMachine.setScanner(new Scanner(System.in));

    return new Object[][]{{vendingMachine, insertedAmount, sysInBackup, sysOutBackup, sysInNew, sysOutNew,}};
  }

  @DataProvider(name = "providerForHasCurrencySelectItemOne")
  public Object[][] providerForHasCurrencySelectItemOne() throws IOException {

    GetPropertyValues getPropertyValues = new GetPropertyValues();
    getPropertyValues.getPropValues();

    final VendingMachine vendingMachine = new VendingMachine();

    final StringBuilder input = new StringBuilder();
    final Items item = Items.values()[new Random().nextInt(Items.values().length)];
    input.append(item.getName()).append("\nN\n");
    final float insertedAmount = item.getPrice();

    InputStream sysInBackup = System.in;
    ByteArrayInputStream sysInNew = new ByteArrayInputStream(input.toString().getBytes());
    System.setIn(sysInNew);

    PrintStream sysOutBackup;
    sysOutBackup = System.out;
    ByteArrayOutputStream sysOutNew;
    sysOutNew = new ByteArrayOutputStream();
    System.setOut(new PrintStream(sysOutNew));

    vendingMachine.setScanner(new Scanner(System.in));

    return new Object[][]{{vendingMachine, insertedAmount, sysInBackup, sysOutBackup, sysInNew, sysOutNew,}};
  }

}
