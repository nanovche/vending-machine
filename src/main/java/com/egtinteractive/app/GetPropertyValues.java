package com.egtinteractive.app;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class GetPropertyValues {

  private InputStream inputStream;

  void getPropValues() throws IOException {

    try{
      Properties prop = new Properties();
      String propFileName = "config.properties";

      inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
      if(inputStream != null){
        prop.load(inputStream);
      } else{
        throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
      }

      int index = 1;
      for (Items item: Items.values()){
        final String value = prop.getProperty("enum" + index);
        final String[] nameAndPrice = value.split(":");
        item.setName(nameAndPrice[0]);
        item.setPrice(Float.parseFloat(nameAndPrice[1]));
        index++;
      }

    } catch (IOException e){
      System.out.println("Exception: " + e);
    } finally {
      if(inputStream != null){
        inputStream.close();

      }
    }
  }

}
