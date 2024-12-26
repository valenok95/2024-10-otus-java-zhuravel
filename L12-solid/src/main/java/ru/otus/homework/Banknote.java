package ru.otus.homework;

import java.util.HashMap;

public abstract class Banknote {
  private final int nominal;

  Banknote(int nominal) {
    this.nominal = nominal;
  }

  public int getNominal() {
    return nominal;
  }

  @Override
  public int hashCode() {
    return nominal;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Banknote) {
      return ((Banknote) obj).getNominal() == nominal;
    }
    return false;
  }
}
