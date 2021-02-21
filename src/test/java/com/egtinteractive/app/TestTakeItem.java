package com.egtinteractive.app;

import static org.testng.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.testng.annotations.Test;

public class TestTakeItem {

  @Test(dataProvider = "providerForMessage", dataProviderClass = Providers.class)
  public void noCurrencyState(VendingMachine vendingMachine, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.takeItem();

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String expected = VendingMachine.getNoCurrencyTakeMessage();
    assertEquals(actual, expected);

    System.setOut(original);
  }

  @Test(dataProvider = "providerForMessage", dataProviderClass = Providers.class)
  public void hasCurrencyState(VendingMachine vendingMachine, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.setState(States.Has_Currency_State);
    vendingMachine.takeItem();

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String expected = VendingMachine.getHasCurrencyTakeMessage();
    assertEquals(actual, expected);

    System.setOut(original);
  }

  @Test(dataProvider = "providerForMessage", dataProviderClass = Providers.class)
  public void itemSelectedState(VendingMachine vendingMachine, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.setState(States.Item_Selected_State);
    vendingMachine.takeItem();

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String expected = VendingMachine.getItemSelectedTakeMessage();
    assertEquals(actual, expected);
    System.setOut(original);
  }

  @Test(dataProvider = "providerForMessage", dataProviderClass = Providers.class)
  public void itemTakenState(VendingMachine vendingMachine, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.setState(States.Item_Taken_State);
    vendingMachine.takeItem();

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String expected = VendingMachine.getItemTakenTakeMessage();
    assertEquals(actual, expected);

    System.setOut(original);
  }

  @Test(dataProvider = "providerForMessage", dataProviderClass = Providers.class)
  public void moneyReturnedState(VendingMachine vendingMachine, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.setState(States.Money_Returned_State);
    vendingMachine.takeItem();

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String expected = VendingMachine.getMoneyReturnedMessage();
    assertEquals(actual, expected);

    System.setOut(original);
  }

}
