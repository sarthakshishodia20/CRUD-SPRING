package in.strikes.crudApplicationDemo.service;

import in.strikes.crudApplicationDemo.entity.Student;
import in.strikes.crudApplicationDemo.repository.StudentRepository;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class StudentService {
        private StudentRepository studentRepository;

        public StudentService(StudentRepository studentRepository){
                this.studentRepository = studentRepository;
        }

        // CREATE
        public Student createStudent(Student student) {
            student.setIsDeleted(0); // ensure new student is active
            return studentRepository.save(student);
        }

        // READ ONE — only active students
        public Student getOneStudent(Long id){
                Student existStudent = studentRepository.findByIdAndIsDeleted(id, 0).orElse(null);
                if(existStudent == null){
                        throw new RuntimeException("Student not found or already deleted");
                }
                return existStudent;
        }

        // READ ALL — only active students
        public List<Student> getAllStudents(){
                return studentRepository.findAllByIsDeleted(0);
        }

        // UPDATE — only active students
        public Student updateStudent(Long id, Student updatedData){
                Student existStudent = studentRepository.findByIdAndIsDeleted(id, 0).orElse(null);
                if(existStudent == null){
                        throw new RuntimeException("Student not found or already deleted");
                }
                existStudent.setName(updatedData.getName());
                existStudent.setAge(updatedData.getAge());
                existStudent.setEmail(updatedData.getEmail());
                existStudent.setRollno(updatedData.getRollno());
                existStudent.setSubject(updatedData.getSubject());
                return studentRepository.save(existStudent);
        }

        // SOFT DELETE — flag set karo, DB mein rehta hai
        public void softDeleteStudent(Long id){
                Student existStudent = studentRepository.findByIdAndIsDeleted(id, 0).orElse(null);
                if(existStudent == null){
                        throw new RuntimeException("Student not found or already deleted");
                }
                existStudent.setIsDeleted(1);
                studentRepository.save(existStudent);
        }

        // HARD DELETE — DB se seedha remove (chahe soft deleted ho ya na ho)
        public void deleteStudent(Long id){
                if(!studentRepository.existsById(id)){
                        throw new RuntimeException("Student not found");
                }
                studentRepository.deleteById(id);
        }

}
