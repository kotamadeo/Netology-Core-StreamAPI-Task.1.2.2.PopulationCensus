package com.gmail.at.kotamadeo;

import com.gmail.at.kotamadeo.enums.Education;
import com.gmail.at.kotamadeo.enums.Sex;
import com.gmail.at.kotamadeo.models.Person;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        System.out.printf("Amount of minors: %d%n%n", getAmountOfMinors(persons));
        getConscriptList(persons, 50);
        System.out.println();
        getWorkersList(persons, 50);

    }

    /**
     * Метод печатающий заданное количество фамилий из списка призывников.
     * @param collection входная коллекция;
     * @param limit ограничивает лимит первым количеством призывников (например, 20) из списка.
     */
    private static void getConscriptList(Collection<Person> collection, int limit) {
        System.out.println("Conscription list: ");
        AtomicInteger counter = new AtomicInteger(1);
        List<?> result = collection.stream()
                .filter(human -> human.getSex().equals(Sex.MAN))
                .filter(human -> human.getAge() >= 18 && human.getAge() <= 27)
                .map(Person::getFamily)
                .toList(); // Collectors.toList() - равнозначны. По конвенции toList() предпочтительнее
        result.stream()
                .limit(limit)
                .forEach(human -> System.out.printf("%d. %s%n", counter.getAndIncrement(), human));
    }

    /**
     * Метод возвращающий количество несовершеннолетних.
     * @param collection входная коллекция;
     * @return возвращает количество несовершеннолетних (людей младше 18 лет).
     */
    private static long getAmountOfMinors(Collection<Person> collection) {
        return collection.stream().filter(human -> human.getAge() < 18).count();
    }

    /**
     * Метод печатающий заданное количество работников с высшим образованием от 18 и до 60-65 лет
     * включительно (для женщин и мужчин, соответственно)).
     * @param collection входная коллекция;
     * @param limit ограничивает лимит первым количеством работников (например, 20) из списка.
     */
    private static void getWorkersList(Collection<Person> collection, int limit) {
        System.out.println("Workers list: ");
        AtomicInteger counter = new AtomicInteger(1);
        List<?> result = collection.stream()
                .filter(human -> human.getEducation().equals(Education.HIGHER))
                .filter(human -> human.getAge() >= 18)
                .filter(human -> (human.getSex().equals(Sex.MAN) && human.getAge() <= 65)
                        || (human.getSex().equals(Sex.WOMAN) && human.getAge() <= 60))
                .sorted(Comparator.comparing(Person::getFamily))
                .toList();
        result.stream()
                .limit(limit)
                .forEach(human -> System.out.printf("%d. %s%n", counter.getAndIncrement(), human));
    }
}