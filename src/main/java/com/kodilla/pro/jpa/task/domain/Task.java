package com.kodilla.pro.jpa.task.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String status;

    @OneToMany(targetEntity = Person.class, mappedBy = "task")
    private final List<Person> persons = new ArrayList<>();;

    @OneToMany(targetEntity = Subtask.class, mappedBy = "task")
    private final Set<Subtask> subtasks = new HashSet<>();;

    public Task() {
    }

    public Task(Long id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public Set<Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
