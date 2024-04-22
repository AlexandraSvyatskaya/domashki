import java.util.HashMap;
import java.util.Map;

public class CustomerStorage {
    private Map<String, Customer> storage; // используй интерфейсы когда объявляешь типы объектов

    public CustomerStorage() {
        storage = new HashMap<>();
    }

    public void addCustomer(String data) {
        String[] components = data.split("\\s+");
        if (components.length != 4) {
            throw new IllegalArgumentException("Неверный формат команды." +
                    "Правильный формат:dd Василий Петров vasily.petrov@gmail.com +79215637722");
        }
        String name = components[0] + " " + components[1];
        char[] charArray = components[3].toCharArray();
        if (charArray[0] != '+' || charArray[1] != '7' || charArray[2] != '9' || charArray.length != 12) {
            throw new IllegalArgumentException("Неверный формат номера телефона.Правильный формат: +79215637722");
        }
        if (!components[2].matches("^[\\w-\\.]+@[\\w-]+(\\.[\\w]+)*\\.[a-z]{2,}$")) {
            throw new IllegalArgumentException("Некоректный ввод mail");
        }
        storage.put(name, new Customer(name, components[3], components[2]));
        // components[3], components[2] - такие вызовы лучше делать единожды и создавать на них переменные.
        // будет проще использовать типа: String phone = components[3]
    }

    public void listCustomers() {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name) {
        storage.remove(name);
    }

    public int getCount() {
        return storage.size();
    }
}