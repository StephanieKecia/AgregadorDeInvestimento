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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


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
    
    @Nested
    class getUsersAll {

        @Test
        @DisplayName("Should return all users with success")
        void shouldReturnAllUsersWithSuccess() {

            //Arrange
            var user = new User(12L,
                    "username",
                    "123",
                    "teste@teste.com",
                    Instant.now(),
                    null
            );

            var userList = List.of(user);
            doReturn(List.of(user)).when(userRepository).findAll();


            //Act
            var output = userService.getUsersAll();
            //Assert
            assertNotNull(output);
            assertEquals(userList.size(), output.size());
        }
    }

    @Nested
    class deleteUserById  {

        @Test
        void shouldDeleteUserWithSuccess() {


            doReturn(true).when(userRepository).existsById(LongArgumentCaptor.capture());

            doNothing().when(userRepository).deleteById(LongArgumentCaptor.capture());

            var userId = 12L;
            //Act
            userService.deleteUserById(Long.valueOf(userId).toString());
            //Assert
            var idList = LongArgumentCaptor.getAllValues();
            assertEquals(userId, idList.get(0));
            assertEquals(userId, idList.get(1));

            verify(userRepository, times(1)).existsById(idList.get(0));
            verify(userRepository, times(1)).deleteById(idList.get(1));

        }

        @Test
        void shouldDeleteUserWhenUserDoesNotExist() {


            doReturn(false).when(userRepository).existsById(LongArgumentCaptor.capture());
            var userId = 12L;
            //Act
            userService.deleteUserById(Long.valueOf(userId).toString());
            //Assert

            assertEquals(userId, LongArgumentCaptor.getValue());

            verify(userRepository, times(1)).existsById(LongArgumentCaptor.getValue());

            verify(userRepository, times(0)).deleteById(any());

        }
    }
    
    @Nested
    class updateUser {

        @Test
        void name() {
        }
    }
}