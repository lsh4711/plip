package com.server.domain.test.service;

import org.springframework.stereotype.Service;

import com.server.domain.test.entity.Test;
import com.server.domain.test.repository.TestRepository;

@Service
public class TestService {
    private TestRepository testRepository;

    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public Test saveTest(Test test) {
        return testRepository.save(test);
    }

    public Test updateTest(Test test) {
        Test foundTest = testRepository.findById(test.getTestId()).get();
        foundTest.setMessage(test.getMessage());

        Test updatedTest = testRepository.save(foundTest);

        return updatedTest;
    }
}
