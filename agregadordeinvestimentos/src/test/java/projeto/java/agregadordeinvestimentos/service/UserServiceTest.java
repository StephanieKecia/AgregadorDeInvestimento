package projeto.java.agregadordeinvestimentos.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projeto.java.agregadordeinvestimentos.dto.CreateUserDto;
import projeto.java.agregadordeinvestimentos.entity.User;
import projeto.java.agregadordeinvestimentos.repository.UserRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;



@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<Long> LongArgumentCaptor;

    @Nested
    class createUser {

        @Test
        @DisplayName("Should create a user with sucess")
        void shouldCreateUserWithSucess() {

            //Arrange
            var user = new User(12L,
                    "username",
                    "123",
                    "teste@teste.com",
                    Instant.now(),
                    null
            );
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new CreateUserDto(
                    "username",
                    "123",
                    "email@email.com");
            //Act
            var output = userService.createUser(input);


            //Assert

            assertNotNull(output);

            var userCaptured = userArgumentCaptor.getValue();
            assertEquals(input.username(),userCaptured.getUsername());
            assertEquals(input.password(),userCaptured.getPassword());
            assertEquals(input.email(),userCaptured.getEmail());
        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowExceptionWhenErrorOccurs() {

            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDto(
                    "username",
                    "123",
                    "email@email.com");
            //Act & Assert
            assertThrows(RuntimeException.class, () -> userService.createUser(input)) ;


        }
    }

    @Nested
    class getUserById {

        @Test
        @DisplayName("Should get user by id with success when optional is present")
        void shouldGetUserByIdWithSuccessWhenOptionalIsPresent() {

            var user = new User(12L,
                    "username",
                    "123",
                    "teste@teste.com",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(user)).when(userRepository).findById(LongArgumentCaptor.capture());

            var output = userService.getUserById(user.getUserId().toString());

            assertTrue(output.isPresent());
            assertEquals(user.getUserId(), LongArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should get user by id with success when optional is empty")
        void shouldGetUserByIdWithSuccessWhenOptionalIsEmpty() {

            var userId = 10L;
            doReturn(Optional.empty()).when(userRepository).findById(LongArgumentCaptor.capture());

            var output = userService.getUserById(Long.toString(userId));

            assertTrue(output.isEmpty());
            assertEquals(userId, LongArgumentCaptor.getValue());
        }

    }
}