package ru.otus.homework;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class MoneyStorageImpl implements MoneyStorage {
  private Map<Banknote, Integer> balance;

  public MoneyStorageImpl(Map<Banknote, Integer> balance) {
    this.balance = balance;
  }

  /**
   * Добавляем купюры в ячейки.
   *
   * @param money набор купюр.
   */
  @Override
  public void addMoney(Map<Banknote, Integer> money) {
    money.forEach((nominal, count) -> balance.put(nominal, count + balance.get(nominal)));
  }

  /**
   * Списываем купюры из хранилища купюр.
   *
   * @param withdrawalMoney количество купюр к списанию.
   */
  @Override
  public void withdrawMoney(Map<Banknote, Integer> withdrawalMoney) {
    withdrawalMoney.forEach(
        (nominal, count) -> {
          if (count > balance.get(nominal)) {
            throw new IssufficientAmountException("Not enough money");
          }
          balance.put(nominal, balance.get(nominal) - count);
        });
  }

  @Override
  public Map<Banknote, Integer> getFullBalance() {
    return Collections.unmodifiableMap(balance);
  }

  @Override
  public String toString() {
    return balance.entrySet().stream()
        .map(entry -> entry.getKey().getNominal() + "рублей: " + entry.getValue() + "шт.")
        .collect(Collectors.joining("\n"));
  }
}
