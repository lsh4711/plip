package com.server.domain.test.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.server.domain.test.entity.Test;
import com.server.domain.test.repository.TestRepository;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

@Service
public class TestService {
    private TestRepository testRepository;

    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public Test saveTest(Test test) {
        long taskId = test.getTaskId();
        Test foundTest = testRepository.findByTaskId(taskId);

        if (foundTest != null) {
            throw new CustomException(ExceptionCode.TASK_EXISTS);
        }

        return testRepository.save(test);
    }

    public Test updateTest(Test test) {
        Test foundTest = testRepository.findById(test.getTestId()).get();
        // foundTest.setMessage(test.getMessage());

        Test updatedTest = testRepository.save(foundTest);

        return updatedTest;
    }

    public Test findTest(long tokenId) {
        Test test = testRepository.findById(tokenId).get();

        return test;
    }

    public List<Test> findTestsOrderByTaskId() {
        Sort sort = Sort.by("taskId");
        List<Test> tests = testRepository.findAll(sort);

        return tests;
    }

    public String getTokenByTaskd(long taskId) {
        Test test = testRepository.findByTaskId(taskId);

        if (test == null) {
            throw new CustomException(ExceptionCode.TASK_NOT_FOUND);
        }

        String accessToken = test.getAccessToken();

        testRepository.delete(test);

        return accessToken;
    }
}
