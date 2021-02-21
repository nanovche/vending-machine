package com.egtinteractive.app;

public interface State {

  void addCurrency(float amount, VendingMachine vendingMachine);

  void selectItem(VendingMachine vendingMachine);

  void makeItem(VendingMachine vendingMachine);

  void takeItem(VendingMachine vendingMachine);

  void returnMoney(VendingMachine vendingMachine);

  void service(VendingMachine vendingMachine);

  void endService(VendingMachine vendingMachine);

}
