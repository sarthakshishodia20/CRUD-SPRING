package in.strikes.crudApplicationDemo.service;

import in.strikes.crudApplicationDemo.dto.StudentRequestDTO;
import in.strikes.crudApplicationDemo.dto.StudentResponseDTO;
import in.strikes.crudApplicationDemo.entity.Student;
import in.strikes.crudApplicationDemo.repository.StudentRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentService {
        private StudentRepository studentRepository;

        public StudentService(StudentRepository studentRepository){
                this.studentRepository = studentRepository;
        }

        // ==================== Helper: DTO → Entity ====================
        // RequestDTO (client ka data) ko Student Entity mein convert karo
        private Student toEntity(StudentRequestDTO dto) {
                Student student = new Student();
                student.setName(dto.getName());
                student.setAge(dto.getAge());
                student.setEmail(dto.getEmail());
                student.setRollno(dto.getRollno());
                student.setSubject(dto.getSubject());
                return student;
        }

        // CREATE — DTO leta hai, Entity banata hai, save karta hai, ResponseDTO return karta hai
        public StudentResponseDTO createStudent(StudentRequestDTO dto) {
                Student student = toEntity(dto);
                student.setIsDeleted(0); // ensure new student is active
                Student saved = studentRepository.save(student);
                return StudentResponseDTO.convertToDTO(saved); // Entity → Response DTO
        }

        // READ ONE — only active students
        public StudentResponseDTO getOneStudent(Long id){
                Student existStudent = studentRepository.findByIdAndIsDeleted(id, 0).orElse(null);
                if(existStudent == null){
                        throw new RuntimeException("Student not found or already deleted");
                }
                return StudentResponseDTO.convertToDTO(existStudent);
        }

        // READ ALL — only active students
        public List<StudentResponseDTO> getAllStudents(){
                // stream() se har ek Student entity ko ResponseDTO mein convert karo
                return studentRepository.findAllByIsDeleted(0)
                        .stream()
                        .map(StudentResponseDTO::convertToDTO)
                        .collect(Collectors.toList());
        }

        // UPDATE — only active students
        public StudentResponseDTO updateStudent(Long id, StudentRequestDTO dto){
                Student existStudent = studentRepository.findByIdAndIsDeleted(id, 0).orElse(null);
                if(existStudent == null){
                        throw new RuntimeException("Student not found or already deleted");
                }
                // DTO se updated values set karo
                existStudent.setName(dto.getName());
                existStudent.setAge(dto.getAge());
                existStudent.setEmail(dto.getEmail());
                existStudent.setRollno(dto.getRollno());
                existStudent.setSubject(dto.getSubject());
                Student updated = studentRepository.save(existStudent);
                return StudentResponseDTO.convertToDTO(updated);
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
