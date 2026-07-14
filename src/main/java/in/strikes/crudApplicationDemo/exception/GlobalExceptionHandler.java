package in.strikes.crudApplicationDemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global Exception Handler — poore app ke liye centralized error handling.
 *
 * @RestControllerAdvice — Spring ko batata hai ki yeh class saare controllers
 * ke exceptions pakad sakti hai. Har controller mein alag try-catch nahi likhna padta.
 *
 * Yahan hum MethodArgumentNotValidException handle kar rahe hain —
 * yeh tab throw hoti hai jab @Valid fail ho jata hai controller mein.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Yeh method tab call hoga jab koi validation fail ho.
     * Jaise: name blank bheja, email invalid diya, age range se bahar hai, etc.
     *
     * Response example agar name aur email dono galat ho:
     * {
     *   "name": "Name khali nahi hona chahiye",
     *   "email": "Valid email dalo jaise abc@gmail.com"
     * }
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        // Har ek failed validation field ke liye error message nikalo
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        // 400 Bad Request return karo — client ne galat data bheja
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Service layer se aane wale RuntimeExceptions (jaise "Student not found") handle karo
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
