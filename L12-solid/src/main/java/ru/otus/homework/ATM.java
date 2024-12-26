package ru.otus.homework;

import java.util.Map;

public interface ATM {
  void depositCache(Map<Banknote, Integer> banknotesToCount);

  void withdrawMoney(int requiredAmountOfMoney);

  Map<Banknote, Integer> showBalance();
}
