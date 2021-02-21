package com.egtinteractive.app;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine {

  private static final String SUCCESSFUL_INSERT_MESSAGE = "You inserted ";
  private static final String HAS_CURRENCY_INSERT_MESSAGE = "You already inserted coins -> select item now !";
  private static final String ITEM_MADE_INSERT_MESSAGE = "You cannot add coins -> your item is being made !";
  private static final String MONEY_RETURNED_INSERT_MESSAGE = "You should end service now !";
  private static final String NO_CURRENCY_SELECT_MESSAGE = "You cannot select item -> insert coins first !";
  private static final String ITEM_MADE_SELECT_MESSAGE = "You cannot select another drink yet -> your current one is coming to you !";
  private static final String ITEM_SELECTED_SELECT_MESSAGE = "If you want another order you should insert coins first !";
  private static final String ITEM_TAKEN_SELECT_MESSAGE = "You cannot select item. You should insert coins first !";
  private static final String MONEY_RETURNED_MESSAGE = "You should end service now !";
  private static final String NO_CURRENCY_MAKE_MESSAGE = "You cannot make item -> insert coins first !";
  private static final String HAS_CURRENCY_MAKE_MESSAGE = "You cannot make item before selecting -> select item first !";
  private static final String ITEM_MADE_MAKE_MESSAGE = "Your item is already made !";
  private static final String ITEM_TAKEN_MAKE_MESSAGE = "You cannot make item. You should insert coins first !";
  private static final String NO_CURRENCY_TAKE_MESSAGE = "You cannot take item -> insert coins first !";
  private static final String HAS_CURRENCY_TAKE_MESSAGE = "You cannot take item before selecting -> select item first !";
  private static final String ITEM_SELECTED_TAKE_MESSAGE = "You cannot take your item before making it -> make item now !";
  private static final String ITEM_TAKEN_TAKE_MESSAGE = "You just took your item ! ";
  private static final String NO_CURRENCY_RETURN_MONEY_MESSAGE = "You cannot get your money back -> you have not inserted any -> insert coins !";
  private static final String NO_CURRENCY_END_SERVICE_MESSAGE = "You cannot end service -> no service is launched - > insert coins !";
  private static final String ITEM_MADE_END_SERVICE_MESSAGE = "You cannot end service !";
  private static final String ITEM_TAKEN_END_SERVICE_MESSAGE = "You cannot end service yet. Take your change first !";
  private static final String ITEM_MADE_RETURN_MONEY_MESSAGE = "Your change will be returned after item is taken from you !";
  private static final String NO_CHANGE_MESSAGE = "You have no change !";
  private static final String CHANGE_MESSAGE = "Your change is ";
  private static final String ITEM_SELECTED_RETURN_MONEY_MESSAGE = "You already selected drink, you cannot get your money back !";
  private static final String HAS_CURRENCY_END_SERVICE_MESSAGE = "You should take your money back, before ending service !";
  private static final String ITEM_SELECTED_END_SERVICE_MESSAGE = "You cannot end service !";
  private static final String ENTER_SELECTION_MESSAGE = "Enter your selection: ";
  private static final String ANOTHER_DRINK_MESSAGE = "Do you want another drink?";
  private static final String YES_OR_NO_MESSAGE = "Press 'Y' for yes or 'N' for no !";
  private static final String UNVALID_BEVERAGE_NAME_MESSAGE = "You did not enter valid beverage name. Try again !";


  public static String getUnvalidBeverageNameMessage() {
    return UNVALID_BEVERAGE_NAME_MESSAGE;
  }

  public static String getYesOrNoMessage() {
    return YES_OR_NO_MESSAGE;
  }

  public static String getAnotherDrinkMessage() {
    return ANOTHER_DRINK_MESSAGE;
  }

  public static String getEnterSelectionMessage() {
    return ENTER_SELECTION_MESSAGE;
  }

  public static String getItemSelectedEndServiceMessage() {
    return ITEM_SELECTED_END_SERVICE_MESSAGE;
  }

  public static String getHasCurrencyEndServiceMessage() {
    return HAS_CURRENCY_END_SERVICE_MESSAGE;
  }

  public static String getItemSelectedReturnMoneyMessage() {
    return ITEM_SELECTED_RETURN_MONEY_MESSAGE;
  }

  public static String getChangeMessage() {
    return CHANGE_MESSAGE;
  }

  public static String getNoChangeMessage() {
    return NO_CHANGE_MESSAGE;
  }

  public static String getItemMadeReturnMoneyMessage() {
    return ITEM_MADE_RETURN_MONEY_MESSAGE;
  }

  public static String getItemTakenEndServiceMessage() {
    return ITEM_TAKEN_END_SERVICE_MESSAGE;
  }

  public static String getItemMadeEndServiceMessage() {
    return ITEM_MADE_END_SERVICE_MESSAGE;
  }

  public static String getNoCurrencyEndServiceMessage() {
    return NO_CURRENCY_END_SERVICE_MESSAGE;
  }

  public static String getNoCurrencyReturnMoneyMessage() {
    return NO_CURRENCY_RETURN_MONEY_MESSAGE;
  }

  public static String getItemTakenTakeMessage() {
    return ITEM_TAKEN_TAKE_MESSAGE;
  }

  public static String getItemSelectedTakeMessage() {
    return ITEM_SELECTED_TAKE_MESSAGE;
  }

  public static String getHasCurrencyTakeMessage() {
    return HAS_CURRENCY_TAKE_MESSAGE;
  }

  public static String getNoCurrencyTakeMessage() {
    return NO_CURRENCY_TAKE_MESSAGE;
  }

  public static String getItemTakenMakeMessage() {
    return ITEM_TAKEN_MAKE_MESSAGE;
  }

  public static String getItemMadeMakeMessage() {
    return ITEM_MADE_MAKE_MESSAGE;
  }

  public static String getHasCurrencyMakeMessage() {
    return HAS_CURRENCY_MAKE_MESSAGE;
  }

  public static String getNoCurrencyMakeMessage() {
    return NO_CURRENCY_MAKE_MESSAGE;
  }

  public static String getMoneyReturnedMessage() {
    return MONEY_RETURNED_MESSAGE;
  }

  public static String getItemTakenSelectMessage() {
    return ITEM_TAKEN_SELECT_MESSAGE;
  }

  public static String getItemMadeSelectMessage() {
    return ITEM_MADE_SELECT_MESSAGE;
  }

  public static String getItemSelectedSelectMessage() {
    return ITEM_SELECTED_SELECT_MESSAGE;
  }


  public static String getNoCurrencySelectMessage() {
    return NO_CURRENCY_SELECT_MESSAGE;
  }

  public static String getMoneyReturnedInsertMessage() {
    return MONEY_RETURNED_INSERT_MESSAGE;
  }

  public static String getItemMadeInsertMessage() {
    return ITEM_MADE_INSERT_MESSAGE;
  }

  public static String getSuccessfulInsertMessage() {
    return SUCCESSFUL_INSERT_MESSAGE;
  }

  public static String getHasCurrencyInsertMessage() {
    return HAS_CURRENCY_INSERT_MESSAGE;
  }

  static final int INITIAL_QUANTITY_FOR_ITEM = 3;
  static final int NUM_OF_ITEMS = Items.values().length;

  private State state;
  private float insertedAmount;
  private float profit;
  private Map<String, Integer> chosenItems;
  private final Map<String, Items> availableItems;
  private final Map<String, ItemQuantityMapping> itemQuantityMappingMap;
  private Scanner scanner;

  public VendingMachine() {
    availableItems = new HashMap<>();
    chosenItems = new LinkedHashMap<>();
    itemQuantityMappingMap = new HashMap<>();
    setCountForItems();
    this.state = States.No_Currency_State;
    fillAvailableItems();
    fillItemQuantityMap();
    scanner = new Scanner(System.in);
  }

  public void setScanner(final Scanner scanner) {
    this.scanner = scanner;
  }

  public Scanner getScanner() {
    return scanner;
  }

  private void setCountForItems() {
    for (Items item: Items.values()){
      item.setCount(INITIAL_QUANTITY_FOR_ITEM);
    }
  }

  State getState() { return state; }
  void setState(final State state) {
    if(state == null){
      throw new IllegalArgumentException();
    }
    this.state = state;
  }
  float getInsertedAmount() {
    return insertedAmount;
  }
  void setInsertedAmount(final float insertedAmount) {
    this.insertedAmount = insertedAmount;
  }
  float getProfit() {
    return profit;
  }
  void setProfit(final float profit) {
    if(profit < 0.0){
      throw new IllegalArgumentException();
    }
    this.profit = profit;
  }

  Map<String, Items> getCopyOfAvailableItems() {
    Map<String, Items> copyOfAvailableItems = new HashMap<>();

    for (Map.Entry<String, Items> entry: availableItems.entrySet()) {
      copyOfAvailableItems.put(entry.getKey(), entry.getValue());
    }

    return copyOfAvailableItems;
  }

  Map<String, Items> getAvailableItems() {
    return availableItems;
  }
  private void fillAvailableItems(){
    for (Items item: Items.values()){
      availableItems.put(item.getName(), item);
    }
  }
  void refillItems(Items item){
    item.setCount(INITIAL_QUANTITY_FOR_ITEM);
    availableItems.put(item.getName(), item);
  }

  Map<String, Integer> getChosenItems() {
    return chosenItems;
  }
  void addItemToChosen(String item, int times){
    chosenItems.put(item, times);
  }
  void removeItemFromChosen(String removedItem){

    final int newQuantity = chosenItems.get(removedItem) - 1;
    chosenItems.put(removedItem, newQuantity);
    if(chosenItems.get(removedItem) == 0){
      chosenItems.remove(removedItem);
    }

  }

  Map<String, ItemQuantityMapping> getItemQuantityMappingMap() {
    return itemQuantityMappingMap;
  }
  private void fillItemQuantityMap() {
    for (Items item: Items.values()){
      itemQuantityMappingMap.put(item.getName(), new ItemQuantityMapping(item));
    }
  }

  public void addCurrency(final float amount) {
    state.addCurrency(amount, this);
  }
  public void selectItem() {
    state.selectItem(this);
  }
  public void makeItem() {
    state.makeItem(this);
  }
  public void takeItem() {
    state.takeItem(this);
  }
  public void returnMoney() {
    state.returnMoney(this);
  }
  public void service() {
    state.service(this);
  }
  public void endService() {
    state.endService(this);
  }

}
