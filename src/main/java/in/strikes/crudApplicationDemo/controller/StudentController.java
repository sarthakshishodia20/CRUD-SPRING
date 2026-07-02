package in.strikes.crudApplicationDemo.controller;

import in.strikes.crudApplicationDemo.entity.Student;
import in.strikes.crudApplicationDemo.service.StudentService;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }
    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
     //create student   
     Student createdStudent = studentService.createStudent(student);
     return ResponseEntity.status(201).body(createdStudent);
    }
    // get one student
    @GetMapping("/get/{id}")
    public ResponseEntity<Student> getOneStudent(@PathVariable Long id){
        try {
            Student student = studentService.getOneStudent(id);
            return ResponseEntity.status(200).body(student);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }
    // getAll
    @GetMapping("/get")
    public ResponseEntity<List<Student>> getAllStudents(){
        try{
            List<Student> allStudents = studentService.getAllStudents();
            return ResponseEntity.status(200).body(allStudents);
        }
        catch(RuntimeException e){
            return ResponseEntity.status(404).build();
        }
    }
    // update student
    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedData){
        try {
            Student updated = studentService.updateStudent(id, updatedData);
            return ResponseEntity.status(200).body(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }
    // soft delete student (flag set, DB mein rehta hai)
    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<String> softDeleteStudent(@PathVariable Long id){
        try {
            studentService.softDeleteStudent(id);
            return ResponseEntity.status(200).body("Student soft deleted! (is_deleted = 1)");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Student not found or already deleted!");
        }
    }
    // hard delete student (DB se seedha remove)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.status(200).body("Student permanently deleted!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Student not found!");
        }
    }
}
