package in.strikes.crudApplicationDemo.controller;

import in.strikes.crudApplicationDemo.dto.StudentRequestDTO;
import in.strikes.crudApplicationDemo.dto.StudentResponseDTO;
import in.strikes.crudApplicationDemo.service.StudentService;
import jakarta.validation.Valid;
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

    // CREATE
    // @Valid — Spring ko trigger karta hai ki StudentRequestDTO ke annotations check karo
    // Agar koi validation fail hua → GlobalExceptionHandler handle karega
    @PostMapping("/create")
    public ResponseEntity<StudentResponseDTO> createStudent(@Valid @RequestBody StudentRequestDTO dto){
        StudentResponseDTO created = studentService.createStudent(dto);
        return ResponseEntity.status(201).body(created);
    }

    // GET ONE
    @GetMapping("/get/{id}")
    public ResponseEntity<StudentResponseDTO> getOneStudent(@PathVariable Long id){
        StudentResponseDTO student = studentService.getOneStudent(id);
        return ResponseEntity.ok(student);
        // Note: RuntimeException ab GlobalExceptionHandler pakdega — try-catch ki zaroorat nahi!
    }

    // GET ALL
    @GetMapping("/get")
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents(){
        List<StudentResponseDTO> allStudents = studentService.getAllStudents();
        return ResponseEntity.ok(allStudents);
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequestDTO dto   // @Valid yahan bhi zaroori hai!
    ){
        StudentResponseDTO updated = studentService.updateStudent(id, dto);
        return ResponseEntity.ok(updated);
    }

    // SOFT DELETE
    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<String> softDeleteStudent(@PathVariable Long id){
        studentService.softDeleteStudent(id);
        return ResponseEntity.ok("Student soft deleted! (is_deleted = 1)");
    }

    // HARD DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student permanently deleted!");
    }
}
