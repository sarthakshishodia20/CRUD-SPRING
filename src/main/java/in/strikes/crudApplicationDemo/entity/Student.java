package in.strikes.crudApplicationDemo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int age;
    private String name;
    private String email;
    private int rollno;
    private String subject;

    @Column(name = "is_deleted", nullable = false)
    private int isDeleted = 0; // 0 = active, 1 = soft deleted

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Student() {
        // No-arg constructor required by JPA
    }

    public Student(Long id, int age, String name, String email, int rollno, String subject) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.email = email;
        this.rollno = rollno;
        this.subject = subject;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getRollno() {
        return rollno;
    }
    public void setRollno(int rollno) {
        this.rollno = rollno;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public int getIsDeleted() {
        return isDeleted;
    }
    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
    @Override
    public String toString() {
        return "Student [age=" + age + ", name=" + name + ", email=" + email + ", rollno=" + rollno + ", subject=" + subject + "]";
    }
}
