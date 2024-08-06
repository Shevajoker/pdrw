package com.pdrw.pdrw.security.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pdrw.pdrw.security.entity.User;
import com.pdrw.pdrw.security.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lang")
@RequiredArgsConstructor
@Tag(name = "Язык")
public class LanguageController {

    private final UserService userService;

    @Operation(summary = "Язык")
    @GetMapping()
    public String getLang() {
        return userService.getLang();
    }

    @Operation(summary = "Обновить язык")
    @JsonProperty("lang")
    @PutMapping()
    public String setLang(@RequestParam @Valid String lang) {
        User user = userService.getCurrentUser();
        user.setLang(lang);
        userService.save(user);
        return userService.getLang();
    }
}