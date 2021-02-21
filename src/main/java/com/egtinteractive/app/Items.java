package com.egtinteractive.app;

enum Items {

  ENUM1,
  ENUM2,
  ENUM3,
  ENUM4,
  ENUM5,
  ENUM6,
  ENUM7,
  ENUM8;

  private String name;
  private float price;
  private int count;

  public final String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  final float getPrice(){
    return this.price;
  }
  public void setPrice(float price) {
    this.price = price;
  }

  final int getCount() {
    return this.count;
  }
  final void setCount(final int count) {
    if(count > 0){
      this.count = count;
    }
  }

}
