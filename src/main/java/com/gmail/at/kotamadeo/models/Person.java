package com.gmail.at.kotamadeo.models;

import com.gmail.at.kotamadeo.enums.Education;
import com.gmail.at.kotamadeo.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {
    private String name;
    private String family;
    private Integer age;
    private Sex sex;
    private Education education;

    @Override
    public String toString() {
        return String.format("%s %s, %d. Sex: %s. Education: %s.", family, name, age, sex, education);
    }
}
