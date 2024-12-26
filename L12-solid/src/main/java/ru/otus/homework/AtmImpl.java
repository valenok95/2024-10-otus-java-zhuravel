package ru.otus.homework;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AtmImpl implements ATM {
  private static final Logger log = LoggerFactory.getLogger(AtmImpl.class);
  private MoneyStorage moneyStorage;

  public AtmImpl(MoneyStorage moneyStorage) {
    this.moneyStorage = moneyStorage;
  }

  @Override
  public void depositCache(Map<Banknote, Integer> banknotesToCount) {
    moneyStorage.addMoney(banknotesToCount);
  }

  @Override
  public void withdrawMoney(int requiredAmountOfMoney) {
    moneyStorage.withdrawMoney(generateBanknoteMapByAmount(requiredAmountOfMoney));
  }

  @Override
  public Map<Banknote, Integer> showBalance() {
    log.info("balance info:\n{} ", moneyStorage.toString());
    return moneyStorage.getFullBalance();
  }

  private Map<Banknote, Integer> generateBanknoteMapByAmount(int amount) {
    if (amount % 100 != 0) {
      throw new IssufficientAmountException("Запрошенное количество не кратно 100");
    }
    int remains = amount;
    Map<Banknote, Integer> banknoteResultMap = new HashMap<>();
    if (remains >= 1000) {
      int banknotesCount = remains / 1000;
       remains = remains-(banknotesCount*1000);
      banknoteResultMap.put(new ThousandBanknote(),banknotesCount);
    }
    if (remains >= 500) {
      int banknotesCount = remains / 500;
       remains = remains-(banknotesCount*500);
      banknoteResultMap.put(new FiveHundredBanknote(), banknotesCount);
    }
    if (remains >= 100) {
      int banknotesCount = remains / 100;
      banknoteResultMap.put(new OneHundredBanknote(), banknotesCount);
    }
    return banknoteResultMap;
  }
}
