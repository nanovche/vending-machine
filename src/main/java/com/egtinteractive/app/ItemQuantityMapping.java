package com.egtinteractive.app;

class ItemQuantityMapping {

  private  final Items item;
  private int quantity;
  private boolean isChosen;

  ItemQuantityMapping(final Items item) {
    this.item = item;
  }

  ItemQuantityMapping(final Items item, final int quantity){
    this.item = item;
    this.quantity = quantity;
  }

  Items getItem() {
    return item;
  }

  boolean isChosen() {
    return this.isChosen;
  }

  void setChosen() {
    this.isChosen = true;
  }

  int getQuantity() {
    return quantity;
  }

  void setQuantity(final int quantity) {
    if(quantity > 0 ){
      this.quantity = quantity;
    }
  }
}
