package com.egtinteractive.app;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import static com.egtinteractive.app.VendingMachine.*;

public enum States implements State {

  No_Currency_State{
    public void selectItem(VendingMachine vendingMachine) { System.out.println(getNoCurrencySelectMessage());}
    public void makeItem(VendingMachine vendingMachine) {System.out.println(getNoCurrencyMakeMessage());}
    public void takeItem(VendingMachine vendingMachine) {System.out.println(getNoCurrencyTakeMessage());}
    public void returnMoney(VendingMachine vendingMachine) {System.out.println(getNoCurrencyReturnMoneyMessage());}
    public void service(VendingMachine vendingMachine) {
    }
    public void endService(VendingMachine vendingMachine) {System.out.println(getNoCurrencyEndServiceMessage());}
  }, Has_Currency_State{
    public void addCurrency(float amount, VendingMachine vendingMachine) {System.out.println(getHasCurrencyInsertMessage());}
    public void selectItem(VendingMachine vendingMachine) {

      System.out.println(getEnterSelectionMessage());
      System.out.println(Arrays.toString(vendingMachine.getAvailableItems().keySet().toArray()));

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

            System.out.println(getAnotherDrinkMessage());

            String anotherDrink;
            do {
              System.out.println(getYesOrNoMessage());
              anotherDrink = sc.nextLine();
            } while (!(anotherDrink.equalsIgnoreCase("Y") || anotherDrink.equalsIgnoreCase("N")));

            if (anotherDrink.equalsIgnoreCase("Y")) {
              selectItem(vendingMachine);
              break;
            } else {
              break;
            }

          } else {
            System.out.println("Insufficient amount to purchase " + chosenItem.getName() + ".");
            System.out.println(
                "You need to insert " + (chosenItem.getPrice() - vendingMachine.getInsertedAmount()));
            vendingMachine.setState(No_Currency_State);
            return;
          }

        } else {
          System.out.println(getUnvalidBeverageNameMessage());
          System.out.println(Arrays.toString(vendingMachine.getAvailableItems().keySet().toArray()));

        }

      } while (true);

      vendingMachine.setState(Item_Selected_State);

    }
    public void makeItem(VendingMachine vendingMachine) {System.out.println(getHasCurrencyMakeMessage());}
    public void takeItem(VendingMachine vendingMachine) {System.out.println(getHasCurrencyTakeMessage());}
    public void service(VendingMachine vendingMachine) {}
    public void endService(VendingMachine vendingMachine) {
      System.out.println(getHasCurrencyEndServiceMessage());
    }
  }, Item_Selected_State{
    public void selectItem(VendingMachine vendingMachine) {
      System.out.println(getItemSelectedSelectMessage());
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
          System.out.println("Which item do you want?");
          System.out.println(Arrays.toString(vendingMachine.getChosenItems().keySet().toArray()));

          Scanner sc = new Scanner(System.in);
          String input;

          while(!vendingMachine.getChosenItems().containsKey(input = sc.nextLine())){
            System.out.println("You should enter valid beverage name !");
            System.out.println(vendingMachine.getChosenItems().toString());
          }

          removedItem = input;
        }

        vendingMachine.removeItemFromChosen(removedItem);
        counter += 1;

        System.out.println("Your " + removedItem + " is being made...");

        if(counter == 1){
          System.out.println("Your drink is ready !");
        }
        else{
          System.out.println("Your drinks are ready !");
        }

      }

      vendingMachine.setState(Item_Made_State);

    }
    public void takeItem(VendingMachine vendingMachine) {
      System.out.println(getItemSelectedTakeMessage());
    }
    public void service(VendingMachine vendingMachine) {}
    public void returnMoney(VendingMachine vendingMachine){ System.out.println(getItemSelectedReturnMoneyMessage());};
    public void endService(VendingMachine vendingMachine){ System.out.println(getItemSelectedEndServiceMessage());}
  }, Item_Made_State{
    public void addCurrency(float amount, VendingMachine vendingMachine) {
      System.out.println(getItemMadeInsertMessage());
    }
    public void selectItem(VendingMachine vendingMachine) {
      System.out.println(getItemMadeSelectMessage());
    }
    public void makeItem(VendingMachine vendingMachine) {
      System.out.println(getItemMadeMakeMessage());
    }
    public void takeItem(VendingMachine vendingMachine) {

      int counter = NUM_OF_ITEMS * INITIAL_QUANTITY_FOR_ITEM;

      for (Map.Entry<String, Items> entry: vendingMachine.getAvailableItems().entrySet()){
        counter -= entry.getValue().getCount();
      }

      if(counter == 1){
        System.out.println("Take you drink !");
      }else{
        System.out.println("Take your drinks !");
      }
      vendingMachine.setState(Item_Taken_State);
    }
    public void returnMoney(VendingMachine vendingMachine){
      System.out.println(getItemMadeReturnMoneyMessage());
    }
    public void service(VendingMachine vendingMachine) {

    }
    public void endService(VendingMachine vendingMachine) {
      System.out.println(getItemMadeEndServiceMessage());
    }
  },
  Item_Taken_State{
    public void selectItem(VendingMachine vendingMachine) {
      System.out.println(getItemTakenSelectMessage());
      vendingMachine.setState(No_Currency_State);
    }
    public void makeItem(VendingMachine vendingMachine) {
      System.out.println(getItemTakenMakeMessage());
      vendingMachine.setState(No_Currency_State);
    }
    public void takeItem(VendingMachine vendingMachine) {

      System.out.println(getItemTakenTakeMessage());
    }
    public void service(VendingMachine vendingMachine) {
    }
    public void endService(VendingMachine vendingMachine) {
      System.out.println(getItemTakenEndServiceMessage());
    }
  }, Money_Returned_State{
    public void addCurrency(float amount, VendingMachine vendingMachine) {
      System.out.println(getMoneyReturnedMessage());
    }
    public void selectItem(VendingMachine vendingMachine) {
      System.out.println(getMoneyReturnedMessage());

    }
    public void makeItem(VendingMachine vendingMachine) {
      System.out.println(getMoneyReturnedMessage());

    }
    public void takeItem(VendingMachine vendingMachine) {
      System.out.println(getMoneyReturnedMessage());

    }
    public void returnMoney(VendingMachine vendingMachine) {
      System.out.println(getMoneyReturnedMessage());

    }
    public void service(VendingMachine vendingMachine) {

    }
  }, Service_State{
    @Override
    public void addCurrency(float amount, VendingMachine vendingMachine) {
      System.out.println();
    }

    @Override
    public void returnMoney(VendingMachine vendingMachine) {
      System.out.println();
    }

    @Override
    public void endService(VendingMachine vendingMachine) {
      System.out.println();
    }

    @Override
    public void selectItem(VendingMachine vendingMachine) {
      System.out.println();
    }

    @Override
    public void makeItem(VendingMachine vendingMachine) {
      System.out.println();
    }

    @Override
    public void takeItem(VendingMachine vendingMachine) {
      System.out.println();
    }

    @Override
    public void service(VendingMachine vendingMachine) {
      System.out.println();
    }

  };

  public void addCurrency(float amount, VendingMachine vendingMachine) {

    if(amount <= 0 ){
      throw new IllegalArgumentException();
    }

    CustomPrintStream.print("You inserted " + Math.round(amount*100.0)/100.0);
//    System.out.print("You inserted " + Math.round(amount*100.0)/100.0);
//    System.out.print(System.lineSeparator());
    vendingMachine.setInsertedAmount(vendingMachine.getInsertedAmount() + amount);
    vendingMachine.setState(Has_Currency_State);

  }
  public void returnMoney(VendingMachine vendingMachine) {

    final float amount = vendingMachine.getInsertedAmount();
    final float change = amount - vendingMachine.getProfit();

    vendingMachine.setInsertedAmount(0.0f);

    if(change == 0){
      System.out.println(getNoChangeMessage());
    }else{
      System.out.println(getChangeMessage() + change);
    }

    vendingMachine.setState(Money_Returned_State);

  }
  public void endService(VendingMachine vendingMachine) {

    for (Items item: Items.values()) {
      if(!vendingMachine.getAvailableItems().containsKey(item.getName())){
        vendingMachine.refillItems(item);
      }
    }

    System.out.println("Machine is ready for next order !");
    vendingMachine.setState(No_Currency_State);

  }

}
