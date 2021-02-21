package com.egtinteractive.app;

import static com.egtinteractive.app.VendingMachine.INITIAL_QUANTITY_FOR_ITEM;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

import org.testng.annotations.Test;

public class TestSelectItem {

  @Test(dataProvider = "providerForMessage", dataProviderClass = Providers.class)
  public void noCurrencyState(VendingMachine vendingMachine, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.selectItem();

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String expected = VendingMachine.getNoCurrencySelectMessage();
    assertEquals(actual, expected);

    System.setOut(original);
  }

  @Test(dataProvider = "providerForHasCurrencySelectItem", dataProviderClass = Providers.class, invocationCount = 10)
  public void hasCurrencyState(final VendingMachine vendingMachine, final float insertedAmount, final InputStream sysInBackup,
      PrintStream sysOutBackup, ByteArrayInputStream sysInNew, ByteArrayOutputStream sysOutNew){

    final Map<String, Items> mapOfAvailableItems = vendingMachine.getCopyOfAvailableItems();

    vendingMachine.setInsertedAmount(insertedAmount);
    vendingMachine.setState(States.Has_Currency_State);
    vendingMachine.selectItem();

    final int lengthOfEnterSelectionMessage = VendingMachine.getEnterSelectionMessage().length();
    final int lengthOfAnotherDrinkMessage = VendingMachine.getAnotherDrinkMessage().length();
    final int lengthOfYesOrNoMessage = VendingMachine.getYesOrNoMessage().length();
    final int lengthOfInvalidBeverageNameMessage = VendingMachine.getUnvalidBeverageNameMessage().length();

    final String outPutStream = sysOutNew.toString().substring(0, sysOutNew.size() - 1).replace("\r\n", "");
    String availableItemsMessage = mapOfAvailableItems.keySet().toString();

    boolean flag = false;
    int currentIndex = 0;

    Set<String> keys = vendingMachine.getChosenItems().keySet();
    for (String order: keys) {

      if (mapOfAvailableItems.get(order) != null && mapOfAvailableItems.get(order).getCount() == 0) {
        mapOfAvailableItems.remove(order);
        availableItemsMessage = mapOfAvailableItems.toString();
        flag = true;
      }

      if(mapOfAvailableItems.get(order) != null){
        final Items item = mapOfAvailableItems.get(order);
        item.setCount(item.getCount() - 1);
        mapOfAvailableItems.put(order, item);
      }

      int lengthOfAvailableItemsMessage = availableItemsMessage.length();

      assertEquals(outPutStream.substring(currentIndex, currentIndex + lengthOfEnterSelectionMessage), VendingMachine.getEnterSelectionMessage());
      currentIndex += lengthOfEnterSelectionMessage;
      assertEquals(outPutStream.substring(currentIndex, currentIndex + lengthOfAvailableItemsMessage), availableItemsMessage);
      currentIndex += lengthOfAvailableItemsMessage;
      if(flag){
        assertEquals(outPutStream.substring(currentIndex, currentIndex + lengthOfInvalidBeverageNameMessage), VendingMachine.getUnvalidBeverageNameMessage());
        currentIndex += lengthOfInvalidBeverageNameMessage;
        flag = false;
      }
      assertEquals(outPutStream.substring(currentIndex, currentIndex + lengthOfAnotherDrinkMessage) , VendingMachine.getAnotherDrinkMessage());
      currentIndex += lengthOfAnotherDrinkMessage;
      assertEquals(outPutStream.substring(currentIndex, currentIndex + lengthOfYesOrNoMessage), VendingMachine.getYesOrNoMessage());
      currentIndex += lengthOfYesOrNoMessage;
    }

    assertEquals(vendingMachine.getProfit(), insertedAmount);
    assertEquals(vendingMachine.getState(), States.Item_Selected_State);

    System.setIn(sysInBackup);
    System.setOut(sysOutBackup);

  }

//  @Test(dataProvider = "providerForHasCurrencySelectItemOne", dataProviderClass = Providers.class)
//  public void hasCurrencyState(VendingMachine vendingMachine,  final float insertedAmount, ByteArrayOutputStream sysOutNew, PrintStream original){
//
//    vendingMachine.setInsertedAmount(insertedAmount);
//    vendingMachine.setState(States.Has_Currency_State);
//    vendingMachine.selectItem();
//
//    final String outputStream = sysOutNew.toString().substring(0, sysOutNew.size() - 1);
//
//    assertEquals(actual, expected);
//    assertEquals(vendingMachine.getState(), States.No_Currency_State);
//
//    System.setOut(original);
//  }

  @Test(dataProvider = "providerForMessage", dataProviderClass = Providers.class)
  public void itemSelectedState(VendingMachine vendingMachine, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.setState(States.Item_Selected_State);
    vendingMachine.selectItem();

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String expected = VendingMachine.getItemSelectedSelectMessage();
    assertEquals(actual, expected);
    assertEquals(vendingMachine.getState(), States.No_Currency_State);

    System.setOut(original);
  }

  @Test(dataProvider = "providerForMessage", dataProviderClass = Providers.class)
  public void itemMadeState(VendingMachine vendingMachine, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.setState(States.Item_Made_State);
    vendingMachine.selectItem();

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String expected = VendingMachine.getItemMadeSelectMessage();
    assertEquals(actual, expected);

    System.setOut(original);
  }

  @Test(dataProvider = "providerForMessage", dataProviderClass = Providers.class)
  public void itemTakenState(VendingMachine vendingMachine, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.setState(States.Item_Taken_State);
    vendingMachine.selectItem();

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String expected = VendingMachine.getItemTakenSelectMessage();
    assertEquals(actual, expected);
    assertEquals(vendingMachine.getState(), States.No_Currency_State);

    System.setOut(original);
  }

  @Test(dataProvider = "providerForMessage", dataProviderClass = Providers.class)
  public void moneyReturnedState(VendingMachine vendingMachine, ByteArrayOutputStream outContent, PrintStream original){

    vendingMachine.setState(States.Money_Returned_State);
    vendingMachine.selectItem();

    final String actual = outContent.toString().substring(0, outContent.size() - 1);
    final String expected = VendingMachine.getMoneyReturnedMessage();
    assertEquals(actual, expected);

    System.setOut(original);
  }

}
