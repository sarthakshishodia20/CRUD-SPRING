package in.strikes.crudApplicationDemo.dto;

import in.strikes.crudApplicationDemo.entity.Student;

/**
 * Response DTO — yeh woh object hai jo SERVER client ko bhejta hai.
 * Isme sirf woh fields hain jo client ko dikhne chahiye.
 * isDeleted jaisa internal field CLIENT ko nahi dikhta — security!
 */
public class StudentResponseDTO {

    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Integer rollno;
    private String subject;

    // Student entity ko Response DTO mein convert karta hai
    // Usage: StudentResponseDTO.convertToDTO(studentEntity)
    public static StudentResponseDTO convertToDTO(Student student) {
        StudentResponseDTO dto = new StudentResponseDTO();
        dto.id = student.getId();
        dto.name = student.getName();
        dto.age = student.getAge();
        dto.email = student.getEmail();
        dto.rollno = student.getRollno();
        dto.subject = student.getSubject();
        // NOTE: isDeleted yahan intentionally nahi rakha — client ko nahi dikhna chahiye
        return dto;
    }

    // ==================== Getters ====================
    // Response DTO mein setters ki zaroorat nahi — yeh sirf read-only output hai

    public Long getId() { return id; }
    public String getName() { return name; }
    public Integer getAge() { return age; }
    public String getEmail() { return email; }
    public Integer getRollno() { return rollno; }
    public String getSubject() { return subject; }
}
