package com.egtinteractive.app;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class Test {

  public static void main(String[] args) throws IOException {

    GetPropertyValues items = new GetPropertyValues();
    items.getPropValues();

    VendingMachine vendingMachine1 = new VendingMachine();
    vendingMachine1.addCurrency(10.0f);
    vendingMachine1.selectItem();
    vendingMachine1.returnMoney();

    vendingMachine1.makeItem();
    vendingMachine1.takeItem();
    vendingMachine1.returnMoney();
    vendingMachine1.endService();
    VendingMachine vendingMachine2 = new VendingMachine();
    vendingMachine2.addCurrency(20.0f);
    vendingMachine2.selectItem();
    vendingMachine2.makeItem();
    vendingMachine2.takeItem();
    vendingMachine2.returnMoney();
    vendingMachine2.endService();

  }

}
