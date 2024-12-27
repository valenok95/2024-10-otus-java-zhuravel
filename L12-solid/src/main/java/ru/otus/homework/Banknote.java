package ru.otus.homework;

public enum Banknote {
  ONE_THOUSAND(1000),
  FIVE_HUNDRED(500),
  ONE_HUNDRED(100);

  private final int nominal;

  Banknote(int nominal) {
    this.nominal = nominal;
  }

  public int getNominal() {
    return nominal;
  }
}
