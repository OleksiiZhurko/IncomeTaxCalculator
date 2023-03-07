package com.incometaxcalculator.data.io;

public enum LogOptions {
  ENTERTAINMENT("Entertainment"),
  BASIC("Basic"),
  TRAVEL("Travel"),
  HEALTH("Health"),
  OTHER("Other");

  private final String name;

  LogOptions(String name) {
    this.name = name;
  }

  public String originalName() {
    return name;
  }
}
