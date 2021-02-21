package com.egtinteractive.app;

import static org.testng.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.testng.annotations.Test;

public class TestEndService {

  @Test(dataProvider = "providerForMessage", dataProviderClass = Providers.class)
  public void noCurrencyState(VendingMachine vendingMachine, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.endService();

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String expected = VendingMachine.getNoCurrencyEndServiceMessage();
    assertEquals(actual, expected);

    System.setOut(original);
  }

  @Test(dataProvider = "providerForMessage", dataProviderClass = Providers.class)
  public void hasCurrencyState(VendingMachine vendingMachine, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.setState(States.Has_Currency_State);
    vendingMachine.endService();

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String expected = VendingMachine.getHasCurrencyEndServiceMessage();
    assertEquals(actual, expected);

    System.setOut(original);
  }

  @Test(dataProvider = "providerForMessage", dataProviderClass = Providers.class)
  public void itemSelectedState(VendingMachine vendingMachine, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.setState(States.Item_Selected_State);
    vendingMachine.endService();

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String expected = VendingMachine.getItemSelectedEndServiceMessage();
    assertEquals(actual, expected);

    System.setOut(original);
  }

  @Test(dataProvider = "providerForMessage", dataProviderClass = Providers.class)
  public void itemMadeState(VendingMachine vendingMachine, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.setState(States.Item_Made_State);
    vendingMachine.endService();

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String expected = VendingMachine.getItemMadeEndServiceMessage();
    assertEquals(actual, expected);

    System.setOut(original);
  }

  @Test(dataProvider = "providerForMessage", dataProviderClass = Providers.class)
  public void itemTakenState(VendingMachine vendingMachine, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.setState(States.Item_Taken_State);
    vendingMachine.endService();

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String expected = VendingMachine.getItemTakenEndServiceMessage();
    assertEquals(actual, expected);

    System.setOut(original);
  }

}
