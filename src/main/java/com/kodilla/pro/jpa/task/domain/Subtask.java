package com.kodilla.pro.jpa.task.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Subtask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String status;

    @OneToMany(targetEntity = Person.class, mappedBy = "subtask")
    private final List<Person> persons = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public Subtask() {
    }

    public Subtask(Long id, String name, String status, Task task) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.task = task;
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

    public Task getTask() {
        return task;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
