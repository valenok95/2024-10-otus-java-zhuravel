package ru.otus.homework;

import java.util.Map;

/** Отвечает за работу с ячейками денег. */
public interface MoneyStorage {

  /**
   * Добавляем купюры в ячейки.
   *
   * @param money набор купюр.
   */
  void addMoney(Map<Banknote, Integer> money);

  /**
   * Списываем купюры из хранилища купюр.
   *
   * @param withdrawalMoney количество купюр к списанию.
   */
  Map<Banknote, Integer> withdrawMoney(Map<Banknote, Integer> withdrawalMoney);

  Map<Banknote, Integer> getFullBalance();
}
