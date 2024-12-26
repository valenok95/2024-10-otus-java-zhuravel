import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.homework.ATM;
import ru.otus.homework.AtmImpl;
import ru.otus.homework.Banknote;
import ru.otus.homework.FiveHundredBanknote;
import ru.otus.homework.IssufficientAmountException;
import ru.otus.homework.MoneyStorage;
import ru.otus.homework.MoneyStorageImpl;
import ru.otus.homework.OneHundredBanknote;
import ru.otus.homework.ThousandBanknote;

class AtmTest {
  private ATM atm;
  private Map<Banknote, Integer> initMap = new HashMap<>();

  @BeforeEach
  void setUp() {
    initMap.put(new OneHundredBanknote(), 5);
    initMap.put(new FiveHundredBanknote(), 5);
    initMap.put(new ThousandBanknote(), 5);
    MoneyStorage moneyStorage = new MoneyStorageImpl(initMap);
    atm = new AtmImpl(moneyStorage);
  }

  @Test
  void atmShowBalanceTest() {

    assertEquals(initMap, atm.showBalance());
  }

  @Test
  void atmDepositTest() {
    Map<Banknote, Integer> expectedBalance = new HashMap<>();
    expectedBalance.put(new OneHundredBanknote(), 7);
    expectedBalance.put(new FiveHundredBanknote(), 5);
    expectedBalance.put(new ThousandBanknote(), 5);
    atm.depositCache(Map.of(new OneHundredBanknote(), 2));

    assertEquals(expectedBalance, atm.showBalance());
  }

  @Test
  void atmWithdrawTest() {
    Map<Banknote, Integer> expectedBalance = new HashMap<>();
    expectedBalance.put(new OneHundredBanknote(), 1);
    expectedBalance.put(new FiveHundredBanknote(), 4);
    expectedBalance.put(new ThousandBanknote(), 4);
    atm.withdrawMoney(1900);

    assertEquals(expectedBalance, atm.showBalance());
  }

  @Test()
  void atmWithdrawMoreThanPossibleTest() {
    Map<Banknote, Integer> expectedBalance = new HashMap<>();
    expectedBalance.put(new OneHundredBanknote(), 1);
    expectedBalance.put(new FiveHundredBanknote(), 4);
    expectedBalance.put(new ThousandBanknote(), 4);
    Assertions.assertThrows(IssufficientAmountException.class, () -> atm.withdrawMoney(5555));
  }
}
