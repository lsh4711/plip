package com.server.domain.test.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.server.domain.test.dto.TestDto;
import com.server.domain.test.entity.Test;
import com.server.domain.test.mapper.TestMapper;
import com.server.domain.test.service.TestService;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;
import com.server.global.utils.UriCreator;

@RestController
@RequestMapping("/test")
public class TestController {
    private TestService testService;
    private TestMapper testMapper;

    public TestController(TestService testService,
            TestMapper testMapper) {
        this.testService = testService;
        this.testMapper = testMapper;
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

    @GetMapping
    public ModelAndView getTest() {
        return new ModelAndView("test.html");
    }

    @GetMapping("/error")
    public void customExceptionTest() {
        throw new CustomException(ExceptionCode.TEST_CODE);
    }
}
