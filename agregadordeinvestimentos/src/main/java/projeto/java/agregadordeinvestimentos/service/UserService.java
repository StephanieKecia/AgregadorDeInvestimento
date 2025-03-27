package projeto.java.agregadordeinvestimentos.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import projeto.java.agregadordeinvestimentos.dto.CreateUserDto;
import projeto.java.agregadordeinvestimentos.dto.UpdateUserDto;
import projeto.java.agregadordeinvestimentos.entity.User;
import projeto.java.agregadordeinvestimentos.repository.UserRepository;

import java.time.Instant;
import java.util.List;
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

    public List<User> getUsersAll(){
        return userRepository.findAll();
    }

    public void deleteUserById(String userId) {
        var id = Long.valueOf(userId);
        var userExists = userRepository.existsById(id);
        if(userExists) {
            userRepository.deleteById(Long.valueOf(userId));
        }
    }

    public void updateUser(String userId, UpdateUserDto updateUserDto) {

        var id = Long.valueOf(userId);
        var userEntity = userRepository.findById(id);

        if(userEntity.isPresent()) {
            var user = userEntity.get();

            if(updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username());
            }
            if(updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
        }
    }
}
