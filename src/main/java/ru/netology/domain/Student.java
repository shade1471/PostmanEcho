package ru.netology.domain;

public class Student {
    private int id;
    private String name;
    private String category;
    private int age;

    public Student(int id, String name, String category, int age) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.age = age;
    }
}
