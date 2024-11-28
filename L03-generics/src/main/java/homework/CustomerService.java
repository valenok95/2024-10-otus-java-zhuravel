package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {
    private final NavigableMap<Customer, String> customersTreeMap =
            new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    // todo: 3. надо реализовать методы этого класса
    // важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {

        // Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        return Map.entry(new Customer(customersTreeMap.firstKey().getId(),
                        customersTreeMap.firstKey().getName(),
                        customersTreeMap.firstKey().getScores()),
                customersTreeMap.firstEntry().getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        if (customersTreeMap.higherKey(customer) != null) {
            return Map.entry(new Customer(customersTreeMap.higherKey(customer).getId(),
                            customersTreeMap.higherKey(customer).getName(),
                            customersTreeMap.higherKey(customer).getScores()),
                    customersTreeMap.higherEntry(customer).getValue());
        }
        return null;
    }

    public void add(Customer customer, String data) {
        customersTreeMap.put(customer, data);
    }
}
