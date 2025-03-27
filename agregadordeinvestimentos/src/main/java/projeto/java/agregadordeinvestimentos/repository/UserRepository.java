package projeto.java.agregadordeinvestimentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projeto.java.agregadordeinvestimentos.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
