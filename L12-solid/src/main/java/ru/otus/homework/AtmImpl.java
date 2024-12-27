package ru.otus.homework;

import static ru.otus.homework.Banknote.FIVE_HUNDRED;
import static ru.otus.homework.Banknote.ONE_HUNDRED;
import static ru.otus.homework.Banknote.ONE_THOUSAND;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AtmImpl implements ATM {
  private static final Logger log = LoggerFactory.getLogger(AtmImpl.class);
  private final MoneyStorage moneyStorage;

  public AtmImpl(MoneyStorage moneyStorage) {
    this.moneyStorage = moneyStorage;
  }

  @Override
  public void depositCache(Map<Banknote, Integer> banknotesToCount) {
    moneyStorage.addMoney(banknotesToCount);
  }

  @Override
  public Map<Banknote, Integer> withdrawMoney(int requiredAmountOfMoney) {
    return moneyStorage.withdrawMoney(generateBanknoteMapByAmount(requiredAmountOfMoney));
  }

  @Override
  public Map<Banknote, Integer> showBalance() {
    log.info("balance info:\n{} ", moneyStorage.toString());
    return moneyStorage.getFullBalance();
  }

  private Map<Banknote, Integer> generateBanknoteMapByAmount(int amount) {
    if (amount % ONE_HUNDRED.getNominal() != 0) {
      throw new IssufficientAmountException(
          String.format("Запрошенное количество не кратно %d", ONE_HUNDRED.getNominal()));
    }
    int remains = amount;
    Map<Banknote, Integer> banknoteResultMap = new HashMap<>();
    if (remains >= ONE_THOUSAND.getNominal()) {
      int banknotesCount = remains / ONE_THOUSAND.getNominal();
      remains = remains - (banknotesCount * ONE_THOUSAND.getNominal());
      banknoteResultMap.put(ONE_THOUSAND, banknotesCount);
    }
    if (remains >= FIVE_HUNDRED.getNominal()) {
      int banknotesCount = remains / FIVE_HUNDRED.getNominal();
      remains = remains - (banknotesCount * FIVE_HUNDRED.getNominal());
      banknoteResultMap.put(FIVE_HUNDRED, banknotesCount);
    }
    if (remains >= ONE_HUNDRED.getNominal()) {
      int banknotesCount = remains / ONE_HUNDRED.getNominal();
      banknoteResultMap.put(ONE_HUNDRED, banknotesCount);
    }
    return banknoteResultMap;
  }
}
