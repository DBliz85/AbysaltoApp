package hr.abysalto.hiring.mid.user;

import hr.abysalto.hiring.mid.user.app.usecase.FindUserByUsernameService;
import hr.abysalto.hiring.mid.user.app.usecase.LoginUserService;
import hr.abysalto.hiring.mid.user.app.usecase.RegisterUserUseCase;
import hr.abysalto.hiring.mid.user.dto.UserDto;
import hr.abysalto.hiring.mid.user.web.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.*;

@WebFluxTest(UserController.class)
class UserControllerSecurityTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private RegisterUserUseCase registerUserUseCase;
    @MockitoBean
    private LoginUserService loginUserService;
    @MockitoBean
    private FindUserByUsernameService findUserService;

    @Test
    @WithMockUser(username = "john")
    void testGetCurrentUser() {
        when(findUserService.findUser("john")).thenReturn(new UserDto(null, "john"));

        webTestClient.get()
                .uri("/v1/api/auth/me")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.username").isEqualTo("john");

        verify(findUserService, times(1)).findUser("john");
    }
}
