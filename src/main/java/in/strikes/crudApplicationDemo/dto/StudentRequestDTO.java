package in.strikes.crudApplicationDemo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) — yeh woh object hai jo CLIENT se aata hai.
 * Entity (Student.java) directly expose nahi karte, uski jagah yeh DTO use karte hain.
 *
 * Fayde:
 *   1. Validation — rules define karo ki client kya bhej sakta hai
 *   2. Security — sensitive fields (jaise isDeleted, id) client ko nahi dikhte
 *   3. Flexibility — API change karo bina DB schema todhe
 */
public class StudentRequestDTO {

    // @NotBlank — null, empty (""), aur whitespace-only (" ") sab reject karta hai
    @NotBlank(message = "Name khali nahi hona chahiye")
    @Size(min = 2, max = 50, message = "Name 2 se 50 characters ke beech hona chahiye")
    private String name;

    // @NotNull — null nahi hona chahiye
    // @Min / @Max — range check karta hai
    @NotNull(message = "Age required hai")
    @Min(value = 5, message = "Age kam se kam 5 honi chahiye")
    @Max(value = 100, message = "Age zyada se zyada 100 ho sakti hai")
    private Integer age;

    // @Email — valid email format check karta hai (xyz@domain.com)
    @NotBlank(message = "Email khali nahi honi chahiye")
    @Email(message = "Valid email dalo jaise abc@gmail.com")
    private String email;

    @NotNull(message = "Roll number required hai")
    @Min(value = 1, message = "Roll number 1 se zyada hona chahiye")
    private Integer rollno;

    @NotBlank(message = "Subject khali nahi hona chahiye")
    @Size(min = 2, max = 100, message = "Subject 2 se 100 characters ke beech hona chahiye")
    private String subject;

    // ==================== Getters & Setters ====================

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getRollno() { return rollno; }
    public void setRollno(Integer rollno) { this.rollno = rollno; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
}
