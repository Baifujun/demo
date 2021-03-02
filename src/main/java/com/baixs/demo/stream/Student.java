package com.baixs.demo.stream;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {
    private String name;
    private int age;
    private int type;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
