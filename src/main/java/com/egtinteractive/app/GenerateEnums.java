//package com.egtinteractive.app;
//
//import java.util.List;
//
//public class GenerateEnums {
//
//  public static Class generateEnum(String className, List<String> enums) {
//    StringBuilder code = new StringBuilder();
//    code.append("package enums; public enum enums." + className + " {\n");
//    for (String s : enums)
//      code.append("\t"+s+",\n");
//    code.append("}");
//    return CompilerUtils.CACHED_COMPILER
//        .loadFromJava("enums."+className, code.toString());
//  }
//
//}
