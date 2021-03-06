package cjtodolist.springtodolist.controller;

import cjtodolist.springtodolist.DTO.UserDto;
import cjtodolist.springtodolist.DTO.UserJoinDto;
import cjtodolist.springtodolist.service.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;

    // 회원 가입
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserJoinDto userJoinDto) {
        if (ObjectUtils.isEmpty(userJoinDto.getUsername())) {
            return new ResponseEntity<>("가입 시 username 은 필수입니다.", HttpStatus.BAD_REQUEST);
        }
        if (ObjectUtils.isEmpty(userJoinDto.getPassword())) {
            return new ResponseEntity<>("가입 시 password 는 필수입니다.", HttpStatus.BAD_REQUEST);
        }
        if (ObjectUtils.isEmpty(userJoinDto.getNickname())) {
            return new ResponseEntity<>("가입 시 nickname 은 필수입니다.", HttpStatus.BAD_REQUEST);
        }

        userService.add(userJoinDto);
        return new ResponseEntity<>("가입 성공", HttpStatus.OK);

    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
        String token = userService.validateUser(userDto);
        return ResponseEntity.ok(token);
    }
}
