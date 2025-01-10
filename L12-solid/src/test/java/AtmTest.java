import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.otus.homework.Banknote.FIVE_HUNDRED;
import static ru.otus.homework.Banknote.ONE_HUNDRED;
import static ru.otus.homework.Banknote.ONE_THOUSAND;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.homework.ATM;
import ru.otus.homework.AtmImpl;
import ru.otus.homework.Banknote;
import ru.otus.homework.IssufficientAmountException;
import ru.otus.homework.MoneyStorage;
import ru.otus.homework.MoneyStorageImpl;

class AtmTest {
  private ATM atm;
  private final Map<Banknote, Integer> initMap = new HashMap<>();

  @BeforeEach
  void setUp() {
    initMap.put(ONE_HUNDRED, 5);
    initMap.put(FIVE_HUNDRED, 5);
    initMap.put(ONE_THOUSAND, 5);
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
    expectedBalance.put(ONE_HUNDRED, 7);
    expectedBalance.put(FIVE_HUNDRED, 5);
    expectedBalance.put(ONE_THOUSAND, 5);
    atm.depositCache(Map.of(ONE_HUNDRED, 2));

    assertEquals(expectedBalance, atm.showBalance());
  }

  @Test
  void atmWithdrawTest() {
    Map<Banknote, Integer> expectedCash = new HashMap<>();
    expectedCash.put(ONE_HUNDRED, 4);
    expectedCash.put(FIVE_HUNDRED, 1);
    expectedCash.put(ONE_THOUSAND, 1);

    assertEquals(expectedCash, atm.withdrawMoney(1900));
  }

  @Test()
  void atmWithdrawMoreThanPossibleTest() {
    Map<Banknote, Integer> expectedBalance = new HashMap<>();
    expectedBalance.put(ONE_HUNDRED, 1);
    expectedBalance.put(FIVE_HUNDRED, 4);
    expectedBalance.put(ONE_THOUSAND, 4);
    Assertions.assertThrows(IssufficientAmountException.class, () -> atm.withdrawMoney(5555));
  }
}
