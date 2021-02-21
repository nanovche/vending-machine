package com.egtinteractive.app;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.testng.annotations.Test;

public class TestReturnMoney {

  @Test(dataProvider = "providerForMessage", dataProviderClass = Providers.class)
  public void noCurrencyState(VendingMachine vendingMachine, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.returnMoney();

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String expected = VendingMachine.getNoCurrencyReturnMoneyMessage();
    assertEquals(actual, expected);

    System.setOut(original);
  }

  @Test(dataProvider = "providerForReturnMoney", dataProviderClass = Providers.class)
  public void hasCurrencyState(VendingMachine vendingMachine, ByteArrayOutputStream outContent, PrintStream original, final float amount){

    vendingMachine.addCurrency(amount);
    vendingMachine.returnMoney();

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String expected = VendingMachine.getChangeMessage()+amount;
    assertTrue(actual.contains(expected));
    assertEquals(vendingMachine.getInsertedAmount(), 0);
    assertEquals(vendingMachine.getState(), States.Money_Returned_State);

    System.setOut(original);
  }

  @Test(dataProvider = "providerForMessage", dataProviderClass = Providers.class)
  public void itemSelectedState(VendingMachine vendingMachine, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.setState(States.Item_Selected_State);
    vendingMachine.returnMoney();

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String expected = VendingMachine.getItemSelectedReturnMoneyMessage();
    assertEquals(actual, expected);

    System.setOut(original);
  }

  @Test(dataProvider = "providerForMessage", dataProviderClass = Providers.class)
  public void itemMadeState(VendingMachine vendingMachine, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.setState(States.Item_Made_State);
    vendingMachine.returnMoney();

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String expected = VendingMachine.getItemMadeReturnMoneyMessage();
    assertEquals(actual, expected);

    System.setOut(original);
  }

  @Test(dataProvider = "providerForReturnMoneyChange", dataProviderClass = Providers.class)
  public void itemTakenStateWithChange(VendingMachine vendingMachine, final float amount, final float profit){

    vendingMachine.setState(States.Item_Taken_State);
    vendingMachine.setInsertedAmount(amount);
    vendingMachine.setProfit(profit);

    PrintStream original = System.out;
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    vendingMachine.returnMoney();

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String expected = VendingMachine.getChangeMessage()+(amount-profit);

    assertEquals(actual,expected);
    assertEquals(vendingMachine.getInsertedAmount(), 0);
    assertEquals(vendingMachine.getState(), States.Money_Returned_State);

    System.setOut(original);

  }

  @Test(dataProvider = "providerForReturnMoneyNoChange", dataProviderClass = Providers.class)
  public void itemTakenStateNoChange(VendingMachine vendingMachine, final float amount){

    vendingMachine.setState(States.Item_Taken_State);
    vendingMachine.setInsertedAmount(amount);
    vendingMachine.setProfit(amount);

    PrintStream original = System.out;
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    vendingMachine.returnMoney();

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String expected = VendingMachine.getNoChangeMessage();

    assertEquals(actual,expected);
    assertEquals(vendingMachine.getInsertedAmount(), 0);
    assertEquals(vendingMachine.getState(), States.Money_Returned_State);

    System.setOut(original);

  }

  @Test(dataProvider = "providerForMessage", dataProviderClass = Providers.class)
  public void moneyReturnedState(VendingMachine vendingMachine, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.setState(States.Money_Returned_State);
    vendingMachine.returnMoney();

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String expected = VendingMachine.getMoneyReturnedMessage();
    assertEquals(actual, expected);

    System.setOut(original);
  }


}
