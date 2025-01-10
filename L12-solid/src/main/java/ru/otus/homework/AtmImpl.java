package ru.otus.homework;

import java.util.Arrays;
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
        Map<Banknote, Integer> banknoteResultMap = new HashMap<>();
        int remains = amount;
        boolean canBeDivided =
                Arrays.stream(Banknote.values()).anyMatch(banknote -> amount % banknote.getNominal() == 0);
        if (!canBeDivided) {
            throw new IssufficientAmountException(
                    String.format("Запрошенное количество %d не может быть разделено на купюры!",
                            amount));
        }
        for (Banknote banknote : Banknote.values()) {
            if (remains >= banknote.getNominal()) {
                int banknotesCount = remains / banknote.getNominal();
                remains = remains - (banknotesCount * banknote.getNominal());
                banknoteResultMap.put(banknote, banknotesCount);
            }
        }
        return banknoteResultMap;
    }
}
