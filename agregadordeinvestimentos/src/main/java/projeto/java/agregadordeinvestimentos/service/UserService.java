package projeto.java.agregadordeinvestimentos.service;

import org.springframework.stereotype.Service;
import projeto.java.agregadordeinvestimentos.dto.CreateUserDto;
import projeto.java.agregadordeinvestimentos.entity.User;
import projeto.java.agregadordeinvestimentos.repository.UserRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long createUser(CreateUserDto createUserDto) {

        var entity = new User(
                createUserDto.username(),
                createUserDto.password(),
                createUserDto.email(),
                Instant.now(),
                null);


            var userSaved = userRepository.save(entity);
            System.out.println("Usuário salvo com sucesso: " + userSaved);
            return userSaved.getUserId();



    }

    public Optional<User> getUserById(String userId) {

        return userRepository.findById(Long.valueOf(userId));
    }


}
