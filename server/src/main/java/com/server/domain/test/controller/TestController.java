package com.server.domain.test.controller;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.util.List;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
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

import org.springframework.web.servlet.ModelAndView;

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

    private final KakaoService kakaoService;

    @Value("${kakao.redirect-url}")
    private String redirecUrl;

    @Value("${kakao.api-key}")
    private String apiKey;

    private Token tokens;
    private String accessToken;
    private String refreshToken;

    @GetMapping
    public ResponseEntity getToken(HttpServletResponse response,
        @RequestParam(value = "code", required = false) String code) throws IOException {
        if (code == null) {
            String location = String.format(
                "https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s/test&response_type=code&scope=talk_message",
                apiKey,
                redirecUrl);
            response.sendRedirect(location);
            return ResponseEntity.ok("로그인 해주세요.");
        }
        try {
            tokens = kakaoService.getTokens(code, tokens);
            accessToken = tokens.getAccess_token();
            refreshToken = tokens.getRefresh_token();
        } catch (Exception exception) {
            return ResponseEntity.ok("카카오 토큰 발급에 실패했습니다. 오류 제보 부탁드립니다.");
        }

        List<Test> tests = testService.findTestsOrderByTaskId();
        List<Long> ids = tests.stream().mapToLong(test -> test.getTaskId()).boxed().collect(Collectors.toList());
        String body = String.format(
            "카카오 토큰 발급에 성공했습니다.\\n이제 메세지를 예약 전송 해보실 수 있습니다.\\n\\n%s/test/{message}?id=15&time=5\\n\\n와 같이 요청을 보내주세요.\\n\\ntime은 초 단위이고 id는 예약할 작업의 id를 지정해주시면 됩니다. id는 다른 작업과 중복될 수 없습니다.\\nid 또는 time을 입력하지않으면 메시지가 즉시 전송됩니다.\\n\\n예시: %s/test/코벤져스 폼 미쳤다. ㄷㄷ?id=1&time=5\\n\\n예약된 작업 id: %s",
            redirecUrl,
            redirecUrl,
            ids);

        return ResponseEntity.ok(body.replace("\\n", "<br />"));
    }

    @GetMapping("/{message}")
    public ResponseEntity sendMessage(HttpServletResponse response,
        @PathVariable("message") String message,
        @RequestParam(value = "id", required = false) Long taskId,
        @RequestParam(value = "time", required = false) Long second) throws IOException {
        if (tokens == null) {
            response.sendRedirect(redirecUrl + "/test");
            return ResponseEntity.ok("인증 필요.");
        }
        if (taskId == null || second == null) {
            kakaoService.sendMessage(accessToken, message);
            String body = String.format("메시지 즉시 전송 성공!\\n\\nmessage: \"%s\"\\n\"나에게 보내기\" 기능 특성상 알림이 울리지 않습니다.",
                message);
            return ResponseEntity.ok(body.replace("\\n", "<br />"));
        }

        Test test = new Test();
        test.setTaskId(taskId);
        test.setAccessToken(accessToken);
        test.setRefreshToken(refreshToken);

        testService.saveTest(test);

        sendNotifications(taskId, message, second);

        List<Test> tests = testService.findTestsOrderByTaskId();
        List<Long> ids = tests.stream().mapToLong(t -> t.getTaskId()).boxed().collect(Collectors.toList());

        String body = String.format(
            "메시지 예약 성공!\\n\\n요청하신 메시지 \"%s\" 가 %d초 뒤 카카오톡으로 전송됩니다.\\n \"나에게 보내기\" 기능 특성상 알림이 울리지 않습니다.\\n\\n현재 작업 id: %d\\n예약된 작업 id: %s",
            message,
            second, taskId, ids);

        return ResponseEntity.ok(body.replace("\\n", "<br />"));
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
    private void sendNotifications(long taskId, String message, long second) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
        Duration delay = Duration.ofSeconds(second);

        // 알림 전송 작업
        executorService.schedule(() -> {
            try {
                String accessToken = testService.getTokenByTaskd(taskId);
                // 카카오 메시지 전송
                kakaoService.sendMessage(accessToken, message);
            } catch (Exception e) {
                System.out.println("에러");
            }
        }, delay.toMillis(), TimeUnit.MILLISECONDS);

    }
}

