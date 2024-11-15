package com.example;

import java.security.Key;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {

       Employee emp1 = new Employee("Oleg", 24);
       System.out.println(emp1.hashCode());
       Employee emp2 = new Employee("Vadim", 25);
       System.out.println(emp2.hashCode());
      

       HashMap<String, String> hashMap1 = new HashMap<>(4);
    
       String oldValue;
       oldValue = hashMap1.put("+725252532", "Denis");
       oldValue = hashMap1.put("+7256522532", "Dasha");
       oldValue =  hashMap1.put("+73443252632", "Masha");
       oldValue =  hashMap1.put("+727862232", "Sasaha");
       oldValue =  hashMap1.put("+7278352532", "Sassdha");
       oldValue =  hashMap1.put("+727862542", "Sashadasa");
       oldValue =  hashMap1.put("+727862533", "Sashfafafa");
       oldValue =  hashMap1.put("+7278622532", "Sashggga");
       oldValue =  hashMap1.put("+7278655532", "Sashg3ga");

       String res = hashMap1.get("7256522532");
       res = hashMap1.get("7156522532");

      oldValue =  hashMap1.remove("7256522532");
      oldValue =  hashMap1.remove("7256522532");



      for ( HashMap<String, String>.Entity element : hashMap1) {
        String value = element.value;
          String key = element.key;
      }
    }
}


// Необходимо доработать структуру класса HashMap,
// реализованную на семинаре.
// У нас появились методы добавления, удаления и
// поиска эдемента под ключ. Добавить возможность
// перебора всех элементов нашей стркутуры данных,
// необходимо добавить несколько элементов, а затем
// перебрать все элементы таблицы используя цикл foreach.
// Подумайте возможно стоит обратиться к интерфесу Iterable.