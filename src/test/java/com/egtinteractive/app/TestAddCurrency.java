package com.egtinteractive.app;

import static org.testng.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.testng.annotations.Test;

public class TestAddCurrency{

  @Test(expectedExceptions = IllegalArgumentException.class, dataProvider = "providerForInSufficientAmountZero", dataProviderClass = Providers.class)
  public void amountInsufficientZeroNoCurrencyState(final VendingMachine vendingMachine, final float amount){
    vendingMachine.addCurrency(amount);
  }

  @Test(expectedExceptions = IllegalArgumentException.class, dataProvider = "providerForInSufficientAmountNegative", dataProviderClass = Providers.class)
  public void amountInsufficientNegativeNoCurrencyState(final VendingMachine vendingMachine, final float amount){
    vendingMachine.addCurrency(amount);
  }

  @Test(dataProvider = "providerForAddCurrency", dataProviderClass = Providers.class)
  public void noCurrencyState(final VendingMachine vendingMachine, final float amount, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.addCurrency(amount);

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String message = VendingMachine.getSuccessfulInsertMessage() + amount;
    assertEquals(actual, message);
    assertEquals(vendingMachine.getInsertedAmount(), amount);
    assertEquals(vendingMachine.getState(), States.Has_Currency_State);

    System.setOut(original);
  }

  @Test(dataProvider = "providerForAddCurrency", dataProviderClass = Providers.class)
  public void hasCurrencyState(final VendingMachine vendingMachine, final float amount, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.setState(States.Has_Currency_State);
    vendingMachine.addCurrency(amount);

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String message = VendingMachine.getHasCurrencyInsertMessage();
    assertEquals(actual, message);

    System.setOut(original);

  }

  @Test(dataProvider = "providerForAddCurrency", dataProviderClass = Providers.class)
  public void itemSelectedState(final VendingMachine vendingMachine, final float amount, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.setState(States.Item_Selected_State);
    vendingMachine.addCurrency(amount);

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String message = VendingMachine.getSuccessfulInsertMessage() + amount;
    assertEquals(actual, message);
    assertEquals(vendingMachine.getState(), States.Has_Currency_State);

    System.setOut(original);

  }

  @Test(dataProvider = "providerForAddCurrency", dataProviderClass = Providers.class)
  public void itemMadeState(final VendingMachine vendingMachine, final float amount, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.setState(States.Item_Made_State);
    vendingMachine.addCurrency(amount);

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String message = VendingMachine.getItemMadeInsertMessage();
    assertEquals(actual, message);

    System.setOut(original);

  }

  @Test(dataProvider = "providerForAddCurrency", dataProviderClass = Providers.class)
  public void itemTakenState(final VendingMachine vendingMachine, final float amount, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.setState(States.Item_Taken_State);
    vendingMachine.addCurrency(amount);

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String message = VendingMachine.getSuccessfulInsertMessage() + amount;
    assertEquals(actual, message);
    assertEquals(vendingMachine.getState(), States.Has_Currency_State);

    System.setOut(original);

  }

  @Test(dataProvider = "providerForAddCurrency", dataProviderClass = Providers.class)
  public void moneyReturnedState(final VendingMachine vendingMachine, final float amount, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.setState(States.Money_Returned_State);
    vendingMachine.addCurrency(amount);

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String message = VendingMachine.getMoneyReturnedInsertMessage();
    assertEquals(actual, message);

    System.setOut(original);

  }

}