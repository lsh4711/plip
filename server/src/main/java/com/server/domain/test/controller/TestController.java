package com.server.domain.test.controller;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.domain.member.entity.Member;
import com.server.domain.test.auth.Token;
import com.server.domain.test.dto.TestDto;
import com.server.domain.test.entity.Test;
import com.server.domain.test.mapper.TestMapper;
import com.server.domain.test.service.KakaoService;
import com.server.domain.test.service.TestService;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;
import com.server.global.utils.UriCreator;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final TestService testService;
    private final TestMapper testMapper;

    // @Value("${kakao.redirect-url}")
    // private String redirecUrl;

    private String accessToken;
    private String refreshToken;
    private Token tokens;
    private final KakaoService kakaoService;

    @GetMapping
    public ResponseEntity get(HttpServletResponse response,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "id", required = false) Long memberId,
            @RequestParam(value = "message", required = false) String message,
            @RequestParam(value = "time", required = false) Long second) throws IOException {
        if (code == null) {
            response.sendRedirect(
                "https://kauth.kakao.com/oauth/authorize?client_id=0454767c5440ffe39451b5e9a84c732e&redirect_uri=https://teamdev.shop:8000/test&response_type=code&scope=talk_message");
            return new ResponseEntity<>("로그인해주세요.", HttpStatus.OK);
        }
        if (memberId == null || message == null || second == null) {
            return new ResponseEntity<>("이제 memberId, message, second를 담아서 요청을 보내주세요.", HttpStatus.OK);
        }

        tokens = kakaoService.getTokens(code, tokens);
        accessToken = tokens.getAccess_token();
        refreshToken = tokens.getRefresh_token();

        Member member = Member.builder()
                .memberId(memberId)
                .build();
        Test test = new Test();
        test.setAccessToken(accessToken);
        test.setRefreshToken(refreshToken);
        test.setMember(member);

        testService.saveTest(test);

        sendNotifications(memberId, message, second);

        return new ResponseEntity<>("메시지 예약 성공", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity postTest(@RequestBody TestDto.Post postDto) {
        Test test = testMapper.postDtoToTest(postDto);

        Test savedTest = testService.saveTest(test);
        URI location = UriCreator.createUri("members", test.getTestId());

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{testId}")
    public ResponseEntity postTest(@PathVariable("testId") long testId,
            @RequestBody TestDto.Patch patchDto) {
        Test test = testMapper.patchDtoToTest(patchDto);
        test.setTestId(testId);

        Test updatedTest = testService.updateTest(test);

        return ResponseEntity.ok().body(updatedTest);
    }

    // @GetMapping
    // public ModelAndView getTest() {
    //     return new ModelAndView("test.html");
    // }

    @GetMapping("/error")
    public void customExceptionTest() {
        throw new CustomException(ExceptionCode.TEST_CODE);
    }

    //알림 보내는 메서드
    @Async
    private void sendNotifications(long memberId, String message, long second) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
        Duration initialDelay = Duration.ofSeconds(second);

        executorService.schedule(() -> {
            // 알림 전송 작업
            try {
                Test test = testService.findTestByMemberId(memberId);
                String token = test.getAccessToken();
                // 카카오 메시지 전송
                kakaoService.sendMessage(token, message);
            } catch (Exception e) {
                System.out.println("에러");
            }
        }, initialDelay.toMillis(), TimeUnit.MILLISECONDS);

    }
}
