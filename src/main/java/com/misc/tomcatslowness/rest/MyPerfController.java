package com.misc.tomcatslowness.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/perftest")
public class MyPerfController {

  @GetMapping(value = "/contextclassloader/{className}", produces = "text/json")
  public String getClassByContextClassLoader(@PathVariable("className") String className) {
    ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
    String json;
    try {
      json = generateClassForNameJsonParams(Class.forName(className, false, contextClassLoader).toString(), contextClassLoader.getClass().toString());
    } catch (ClassNotFoundException e) {
      json = generateClassForNameJsonParams("ClassNotFound", contextClassLoader.getClass().toString());
    }
    return json;
  }

  @GetMapping(value = "/normalclassloader/{className}", produces = "text/json")
  public String getClassByNormalClassLoader(@PathVariable("className") String className) {
    ClassLoader normalClassLoader = MyPerfController.class.getClassLoader();
    String json;
    try {

      json = generateClassForNameJsonParams(Class.forName(className, false, normalClassLoader).toString(), normalClassLoader.getClass().toString());
    } catch (ClassNotFoundException e) {
      json = generateClassForNameJsonParams("ClassNotFound", normalClassLoader.getClass().toString());
    }
    return json;
  }

  private static String generateClassForNameJsonParams(String classLoaded, String classLoaderClass) {
    StringBuilder json = new StringBuilder("{");
    json.append("\"classLoader\" : \"").append(classLoaderClass).append("\",");
    json.append("\"classLoaded\" : \"").append(classLoaded).append("\"");
    json.append("}");
    return json.toString();
  }
}
