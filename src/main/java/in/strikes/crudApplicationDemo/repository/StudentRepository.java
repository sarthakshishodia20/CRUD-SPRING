package in.strikes.crudApplicationDemo.repository;

import in.strikes.crudApplicationDemo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Only fetch active (not soft-deleted) students
    List<Student> findAllByIsDeleted(int isDeleted);
    Optional<Student> findByIdAndIsDeleted(Long id, int isDeleted);
}
