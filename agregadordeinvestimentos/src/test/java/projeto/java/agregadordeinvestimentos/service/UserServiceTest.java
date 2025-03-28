package projeto.java.agregadordeinvestimentos.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projeto.java.agregadordeinvestimentos.dto.CreateUserDto;
import projeto.java.agregadordeinvestimentos.repository.UserRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Nested
    class createUser {

        @Test
        @DisplayName("Should create a user with sucess")
        void shouldCreateUserWithSucess() {

            //Arrange
            doReturn(null).when(userRepository).save(any());
            var input = new CreateUserDto(
                    "username",
                    "123",
                    "email@email.com");
            //Act
            userService.createUser(input);
            //Assert

        }
    }

}