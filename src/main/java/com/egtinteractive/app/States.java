package com.egtinteractive.app;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import static com.egtinteractive.app.VendingMachine.*;

public enum States implements State {

  No_Currency_State{
    public void selectItem(VendingMachine vendingMachine) { CustomPrintStream.print(getNoCurrencySelectMessage());}
    public void makeItem(VendingMachine vendingMachine) {CustomPrintStream.print(getNoCurrencyMakeMessage());}
    public void takeItem(VendingMachine vendingMachine) {CustomPrintStream.print(getNoCurrencyTakeMessage());}
    public void returnMoney(VendingMachine vendingMachine) {CustomPrintStream.print(getNoCurrencyReturnMoneyMessage());}
    public void service(VendingMachine vendingMachine) {
    }
    public void endService(VendingMachine vendingMachine) {CustomPrintStream.print(getNoCurrencyEndServiceMessage());}
  }, Has_Currency_State{
    public void addCurrency(float amount, VendingMachine vendingMachine) {CustomPrintStream.print(getHasCurrencyInsertMessage());}
    public void selectItem(VendingMachine vendingMachine) {

      CustomPrintStream.print(getEnterSelectionMessage());
      CustomPrintStream.print(Arrays.toString(vendingMachine.getAvailableItems().keySet().toArray()));

      boolean itemPresentInList;
      Items chosenItem;

      String input;
      final Scanner sc = vendingMachine.getScanner();
      do {
        input = sc.nextLine();

        itemPresentInList = vendingMachine.getAvailableItems().containsKey(input);
        if (itemPresentInList) {

          chosenItem = vendingMachine.getAvailableItems().get(input);

          final boolean amountIsSufficient = vendingMachine.getInsertedAmount() >= chosenItem.getPrice();
          if (amountIsSufficient) {

            vendingMachine.setProfit(vendingMachine.getProfit() + chosenItem.getPrice());

            ItemQuantityMapping currentMapping = vendingMachine.getItemQuantityMappingMap().get(chosenItem.getName());
            if (currentMapping.isChosen()) {
              currentMapping.setQuantity(currentMapping.getQuantity() + 1);
            } else {
              currentMapping.setQuantity(1);
              currentMapping.setChosen();
            }

            vendingMachine.addItemToChosen(chosenItem.getName(), currentMapping.getQuantity());
            chosenItem.setCount(chosenItem.getCount() - 1);

            CustomPrintStream.print(getAnotherDrinkMessage());

            String anotherDrink;
            do {
              CustomPrintStream.print(getYesOrNoMessage());
              anotherDrink = sc.nextLine();
            } while (!(anotherDrink.equalsIgnoreCase("Y") || anotherDrink.equalsIgnoreCase("N")));

            if (anotherDrink.equalsIgnoreCase("Y")) {
              selectItem(vendingMachine);
              break;
            } else {
              break;
            }

          } else {
            CustomPrintStream.print("Insufficient amount to purchase " + chosenItem.getName() + ".");
            CustomPrintStream.print(
                "You need to insert " + (chosenItem.getPrice() - vendingMachine.getInsertedAmount()));
            vendingMachine.setState(No_Currency_State);
            return;
          }

        } else {
          CustomPrintStream.print(getUnvalidBeverageNameMessage());
          CustomPrintStream.print(Arrays.toString(vendingMachine.getAvailableItems().keySet().toArray()));

        }

      } while (true);

      vendingMachine.setState(Item_Selected_State);

    }
    public void makeItem(VendingMachine vendingMachine) {CustomPrintStream.print(getHasCurrencyMakeMessage());}
    public void takeItem(VendingMachine vendingMachine) {CustomPrintStream.print(getHasCurrencyTakeMessage());}
    public void service(VendingMachine vendingMachine) {}
    public void endService(VendingMachine vendingMachine) {
      CustomPrintStream.print(getHasCurrencyEndServiceMessage());
    }
  }, Item_Selected_State{
    public void selectItem(VendingMachine vendingMachine) {
      CustomPrintStream.print(getItemSelectedSelectMessage());
      vendingMachine.setState(No_Currency_State);
    }
    public void makeItem(VendingMachine vendingMachine) {

      int counter = 0;

      while(vendingMachine.getChosenItems().size() != 0){

        String removedItem = null;

        if(vendingMachine.getChosenItems().size() == 1){

          for (Map.Entry<String, Integer> lastEntry: vendingMachine.getChosenItems().entrySet()) {
            removedItem = lastEntry.getKey();
          }

        }else{
          CustomPrintStream.print("Which item do you want?");
          CustomPrintStream.print(Arrays.toString(vendingMachine.getChosenItems().keySet().toArray()));

          Scanner sc = new Scanner(System.in);
          String input;

          while(!vendingMachine.getChosenItems().containsKey(input = sc.nextLine())){
            CustomPrintStream.print("You should enter valid beverage name !");
            CustomPrintStream.print(vendingMachine.getChosenItems().toString());
          }

          removedItem = input;
        }

        vendingMachine.removeItemFromChosen(removedItem);
        counter += 1;

        CustomPrintStream.print("Your " + removedItem + " is being made...");

        if(counter == 1){
          CustomPrintStream.print("Your drink is ready !");
        }
        else{
          CustomPrintStream.print("Your drinks are ready !");
        }

      }

      vendingMachine.setState(Item_Made_State);

    }
    public void takeItem(VendingMachine vendingMachine) {
      CustomPrintStream.print(getItemSelectedTakeMessage());
    }
    public void service(VendingMachine vendingMachine) {}
    public void returnMoney(VendingMachine vendingMachine){ CustomPrintStream.print(getItemSelectedReturnMoneyMessage());};
    public void endService(VendingMachine vendingMachine){ CustomPrintStream.print(getItemSelectedEndServiceMessage());}
  }, Item_Made_State{
    public void addCurrency(float amount, VendingMachine vendingMachine) {
      CustomPrintStream.print(getItemMadeInsertMessage());
    }
    public void selectItem(VendingMachine vendingMachine) {
      CustomPrintStream.print(getItemMadeSelectMessage());
    }
    public void makeItem(VendingMachine vendingMachine) {
      CustomPrintStream.print(getItemMadeMakeMessage());
    }
    public void takeItem(VendingMachine vendingMachine) {

      int counter = NUM_OF_ITEMS * INITIAL_QUANTITY_FOR_ITEM;

      for (Map.Entry<String, Items> entry: vendingMachine.getAvailableItems().entrySet()){
        counter -= entry.getValue().getCount();
      }

      if(counter == 1){
        CustomPrintStream.print("Take you drink !");
      }else{
        CustomPrintStream.print("Take your drinks !");
      }
      vendingMachine.setState(Item_Taken_State);
    }
    public void returnMoney(VendingMachine vendingMachine){
      CustomPrintStream.print(getItemMadeReturnMoneyMessage());
    }
    public void service(VendingMachine vendingMachine) {

    }
    public void endService(VendingMachine vendingMachine) {
      CustomPrintStream.print(getItemMadeEndServiceMessage());
    }
  },
  Item_Taken_State{
    public void selectItem(VendingMachine vendingMachine) {
      CustomPrintStream.print(getItemTakenSelectMessage());
      vendingMachine.setState(No_Currency_State);
    }
    public void makeItem(VendingMachine vendingMachine) {
      CustomPrintStream.print(getItemTakenMakeMessage());
      vendingMachine.setState(No_Currency_State);
    }
    public void takeItem(VendingMachine vendingMachine) {

      CustomPrintStream.print(getItemTakenTakeMessage());
    }
    public void service(VendingMachine vendingMachine) {
    }
    public void endService(VendingMachine vendingMachine) {
      CustomPrintStream.print(getItemTakenEndServiceMessage());
    }
  }, Money_Returned_State{
    public void addCurrency(float amount, VendingMachine vendingMachine) {
      CustomPrintStream.print(getMoneyReturnedMessage());
    }
    public void selectItem(VendingMachine vendingMachine) {
      CustomPrintStream.print(getMoneyReturnedMessage());

    }
    public void makeItem(VendingMachine vendingMachine) {
      CustomPrintStream.print(getMoneyReturnedMessage());

    }
    public void takeItem(VendingMachine vendingMachine) {
      CustomPrintStream.print(getMoneyReturnedMessage());

    }
    public void returnMoney(VendingMachine vendingMachine) {
      CustomPrintStream.print(getMoneyReturnedMessage());

    }
    public void service(VendingMachine vendingMachine) {

    }
  }, Service_State{
    @Override
    public void addCurrency(float amount, VendingMachine vendingMachine) {
      CustomPrintStream.print();
    }

    @Override
    public void returnMoney(VendingMachine vendingMachine) {
      CustomPrintStream.print();
    }

    @Override
    public void endService(VendingMachine vendingMachine) {
      CustomPrintStream.print();
    }

    @Override
    public void selectItem(VendingMachine vendingMachine) {
      CustomPrintStream.print();
    }

    @Override
    public void makeItem(VendingMachine vendingMachine) {
      CustomPrintStream.print();
    }

    @Override
    public void takeItem(VendingMachine vendingMachine) {
      CustomPrintStream.print();
    }

    @Override
    public void service(VendingMachine vendingMachine) {
      CustomPrintStream.print();
    }

  };

  public void addCurrency(float amount, VendingMachine vendingMachine) {

    if(amount <= 0 ){
      throw new IllegalArgumentException();
    }

    CustomPrintStream.print("You inserted " + Math.round(amount*100.0)/100.0);
    vendingMachine.setInsertedAmount(vendingMachine.getInsertedAmount() + amount);
    vendingMachine.setState(Has_Currency_State);

  }
  public void returnMoney(VendingMachine vendingMachine) {

    final float amount = vendingMachine.getInsertedAmount();
    final float change = amount - vendingMachine.getProfit();

    vendingMachine.setInsertedAmount(0.0f);

    if(change == 0){
      CustomPrintStream.print(getNoChangeMessage());
    }else{
      CustomPrintStream.print(getChangeMessage() + change);
    }

    vendingMachine.setState(Money_Returned_State);

  }
  public void endService(VendingMachine vendingMachine) {

    for (Items item: Items.values()) {
      if(!vendingMachine.getAvailableItems().containsKey(item.getName())){
        vendingMachine.refillItems(item);
      }
    }

    CustomPrintStream.print("Machine is ready for next order !");
    vendingMachine.setState(No_Currency_State);

  }

}
