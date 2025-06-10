package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final String ALMATY_TITLE = "Almaty";

    public static void main(String[] args) {
        //Easy
        String[] names = {"Aibek", "Sanzhar", "Temirlan", "Alexander"};
        int[] ages = {24, 17, 14, 27};
        String[] cities = {"Almaty", "Astana", "Almaty", "Moscow"};
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            personList.add(Person.builder()
                    .name(names[i])
                    .age(ages[i])
                    .city(cities[i])
                    .build());
        }

        //Medium
        List<Person> adults = personList.stream()
                .filter(person -> person.getAge() > 18)
                .toList();
        Double averageAge = personList.stream().mapToInt(Person::getAge).average().orElse(0);
        List<Person> almatyPersonList = personList.stream()
                .filter(person -> person.getCity().equals(ALMATY_TITLE))
                .toList();
        Map<String, Integer> personMap = personList.stream()
                .collect(Collectors.toMap(Person::getName, Person::getAge));

        //Hard
        List<Person> limitedPersonList = personList.stream()
                .sorted(Comparator.comparing(Person::getAge, Comparator.reverseOrder()))
                .limit(3).toList();
        List<Person> googleEmployees = personList.stream()
                .sorted(Comparator.comparing(Person::getName))
                .limit(personList.size() / 2)
                .toList();
        List<Person> amazonEmployees = personList.stream()
                .sorted(Comparator.comparing(Person::getName, Comparator.reverseOrder()))
                .limit(personList.size() / 2)
                .toList();

        Company google = Company.builder()
                .name("Google")
                .employees(googleEmployees)
                .build();
        Company amazon = Company.builder()
                .name("Amazon")
                .employees(amazonEmployees)
                .build();
        List<Person> overTwentyFiveList = google.getEmployees().stream()
                .filter(person -> person.getAge() > 25)
                .toList();
        List<Company> companyList = new ArrayList<>();
        companyList.add(google);
        companyList.add(amazon);
        OptionalDouble employeesAverageAge = companyList.stream()
                .flatMap(company -> company.getEmployees().stream())
                .mapToInt(Person::getAge)
                .average();
        Map<String, Double> averageAgeInCompanyMap = companyList.stream()
                .collect(
                        Collectors.toMap(Company::getName,
                        company -> company.getEmployees().stream()
                                .mapToInt(Person::getAge)
                                .average()
                                .orElse(0))
                );
        System.out.println(averageAgeInCompanyMap);
        System.out.println(overTwentyFiveList);
    }
}