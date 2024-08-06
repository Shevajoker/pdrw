package com.pdrw.pdrw.security.rest;

import com.pdrw.pdrw.security.entity.JwtAuthenticationResponse;
import com.pdrw.pdrw.security.entity.SignInRequest;
import com.pdrw.pdrw.security.entity.SignUpRequest;
import com.pdrw.pdrw.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request,
                                            @RequestParam(required = false, defaultValue = "ru") String lang) {
        return authenticationService.signUp(request, lang);
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }
}
