package com.kodilla.pro.jpa.task;

import com.kodilla.pro.jpa.domain.Invoice;
import com.kodilla.pro.jpa.domain.Item;
import com.kodilla.pro.jpa.task.domain.Person;
import com.kodilla.pro.jpa.task.domain.Subtask;
import com.kodilla.pro.jpa.task.domain.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class TaskTestSuite {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    void shouldShowAllDataWithOneQuery() {
        //Given
        List<Long> savedTasks = insertExampleData();
        EntityManager em = emf.createEntityManager();

        //When
        System.out.println("****************** BEGIN OF FETCHING *******************");
        System.out.println("*** STEP 1 – query for tasks ***");

//        List<Task> tasks =
//                em.createQuery(
//                        "from Task "
//                                + " where id in (" + taskIds(savedTasks) + ")",
//                        Task.class).getResultList();

        TypedQuery<Task> query = em.createQuery(
                "from Task "
                        + " where id in (" + taskIds(savedTasks) + ")",
                Task.class);

        EntityGraph<Task> eg = em.createEntityGraph(Task.class);

        eg.addSubgraph("persons").addAttributeNodes("subtask");
        eg.addSubgraph("subtasks");
        query.setHint("javax.persistence.fetchgraph", eg);

        List<Task> tasks = query.getResultList();

        for (Task task : tasks) {
            System.out.println("*** STEP 2 – read data from task ***");
            System.out.println(task);

            for (Subtask subtask : task.getSubtasks()) {
                System.out.println("*** STEP 3 – read the subtask ***");
                System.out.println(subtask);
                System.out.println("*** STEP 4 – read the task from subtask ***");
                System.out.println(subtask.getTask());
            }

            for (Person person : task.getPersons()) {
                System.out.println("*** STEP 5 – read the person ***");
                System.out.println(person);
                System.out.println("*** STEP 6 – read the task from person ***");
                System.out.println(person.getTask());
                System.out.println("*** STEP 7 – read the subtask from person ***");
                System.out.println(person.getSubtask());
            }

        }

        System.out.println("****************** END OF FETCHING *******************");

        //Then
        //Here should be some assertions and the clean up performed

    }

    private String taskIds(List<Long> taskIds) {
        return taskIds.stream()
                .map(n -> "" + n)
                .collect(Collectors.joining(","));
    }


    private List<Long> insertExampleData() {
        Task t1 = new Task(null, "Task1", "Status1");
        Task t2 = new Task(null, "Task2", "Status2");
        Subtask s1 = new Subtask(null, "Subtask1", "Status1", t1);
        Subtask s2 = new Subtask(null, "Subtask2", "Status2", t1);
        Subtask s3 = new Subtask(null, "Subtask3", "Status3", t2);
        Subtask s4 = new Subtask(null, "Subtask4", "Status4", t2);
        Person p1 = new Person(null, "FirstName1", "LastName1", t1, s1);
        Person p2 = new Person(null, "FirstName2", "LastName2", t1, s2);
        Person p3 = new Person(null, "FirstName3", "LastName3", t2, s3);
        Person p4 = new Person(null, "FirstName4", "LastName4", t2, s4);
        t1.getSubtasks().addAll(List.of(s1, s2));
        t1.getPersons().addAll(List.of(p1, p2));
        t2.getSubtasks().addAll(List.of(s3, s4));
        t2.getPersons().addAll(List.of(p3, p4));
        s1.getPersons().add(p1);
        s2.getPersons().add(p2);
        s3.getPersons().add(p3);
        s4.getPersons().add(p4);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(t1);
        em.persist(t2);
        em.persist(s1);
        em.persist(s2);
        em.persist(s3);
        em.persist(s4);
        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        em.persist(p4);
        em.flush();
        em.getTransaction().commit();
        em.close();

        return List.of(t1.getId(), t2.getId());
    }
}
