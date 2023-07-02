package com.projects7.controller;

import com.projects7.dto.PersonDtoInput;
import com.projects7.dto.wrapped.DtoWrapper;
import com.projects7.dto.wrapped.PersonDtoWrapped;
import com.projects7.security.PersonDetails;
import com.projects7.service.PersonDetailsService;
import com.projects7.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    private final PersonDetailsService personDetailsService;

    private final RegistrationService registrationService;

    @Autowired
    public PersonController(PersonDetailsService personDetailsService, RegistrationService registrationService) {
        this.personDetailsService = personDetailsService;
        this.registrationService = registrationService;
    }

    @Operation(summary = "Регистрация нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь создан",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не создан",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    @PostMapping("/CreateUser")
    public ResponseEntity<DtoWrapper> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = PersonDtoInput.class)))
            @RequestBody PersonDtoInput personDtoInput) {
        DtoWrapper result = null;
        result = registrationService.createUser(personDtoInput);
        return new ResponseEntity<>(result, result == null ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED);
    }

    @Operation(summary = "Получение списка пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователи возвращены",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDtoWrapped.class))),
            @ApiResponse(responseCode = "404", description = "Пользователи не возвращены",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    @GetMapping("/GetUsers")
    public ResponseEntity<PersonDtoWrapped> getUsers() {
        PersonDtoWrapped result = null;
        result = personDetailsService.getUsers();
        return new ResponseEntity<>(result, result == null ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED);
    }

    @Operation(summary = "Все данные пользователя по имени пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные получены",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDetails.class))),
            @ApiResponse(responseCode = "404", description = "Данные не получены",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsernameNotFoundException.class)))})
    @GetMapping("/LoadUserByUsername")
    public ResponseEntity<UserDetails> loadUserByUsername(
            @Parameter(description = "(required) username ") @RequestParam(value = "userName", required = true) String userName
    ) {
        UserDetails result = null;
        result = personDetailsService.loadUserByUsername(userName);
        return new ResponseEntity<>(result, result == null ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED);
    }

    @Operation(summary = "Авторизация пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "JVT токен получен",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "JVT токен не получен",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    @PostMapping("/Authorization")
    public ResponseEntity<String> authorization(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = PersonDtoInput.class)))
            @RequestBody PersonDtoInput personDtoInput
    ) {
        String result = null;
        result = personDetailsService.authorization(personDtoInput);
        return new ResponseEntity<>(result, result == null ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED);
    }

    @Operation(summary = "Поиск пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователи найдены",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDtoWrapped.class))),
            @ApiResponse(responseCode = "404", description = "Пользователи не найдены",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDtoWrapped.class)))})
    @GetMapping("/findPersonByUsername")
    public ResponseEntity<PersonDtoWrapped> findPersonByUsername(
            @Parameter(description = "(required) token") @RequestParam(value = "token", required = true) String token,
            @Parameter(description = "(required) username") @RequestParam(value = "userName", required = true) String userName
    ) throws Exception {
        PersonDtoWrapped result = null;
        result = personDetailsService.findPersonByUsername(token, userName);
        return new ResponseEntity<>(result, result == null ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED);
    }

    @Operation(summary = "Добавление пользователея в друзья")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь добавленв друзья",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь добавленв друзья",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    @PostMapping("/AddFriend")
    public ResponseEntity<DtoWrapper> addFriend(
            @Parameter(description = "(required) token") @RequestParam(value = "token", required = true) String token,
            @Parameter(description = "(required) userId") @RequestParam(value = "userId", required = true) String userId
    ) throws Exception {
        DtoWrapper result = null;
        result = personDetailsService.addFriend(token, userId);
        return new ResponseEntity<>(result, result == null ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED);
    }

    @Operation(summary = "Удаление пользователея в друзья")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь удален из друзей",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не удален из друзей",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    @DeleteMapping("/DeleteFriend")
    public ResponseEntity<DtoWrapper> deleteFriend(
            @Parameter(description = "(required) token") @RequestParam(value = "token", required = true) String token,
            @Parameter(description = "(required) userId") @RequestParam(value = "userId", required = true) String userId
    ) throws Exception {
        DtoWrapper result = null;
        result = personDetailsService.deleteFriend(token, userId);
        return new ResponseEntity<>(result, result == null ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED);
    }

    @Operation(summary = "Вывод списка друзей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список друзей выведен",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDtoWrapped.class))),
            @ApiResponse(responseCode = "404", description = "Список друзей не выведен",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    @GetMapping("/GetAllFriends")
    public ResponseEntity<PersonDtoWrapped> getAllFriends(
            @Parameter(description = "(required) token") @RequestParam(value = "token", required = true) String token
    ) throws Exception {
        PersonDtoWrapped result = null;
        result = personDetailsService.getAllFriends(token);
        return new ResponseEntity<>(result, result == null ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED);
    }






}
