package com.server.domain.push.service;

import org.springframework.stereotype.Service;

import com.server.domain.push.entity.Push;
import com.server.domain.push.repository.PushRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PushService {
    private final PushRepository pushRepository;

    public Push savePush(Push push) {
        Push savedPush = pushRepository.save(push);

        return savedPush;
    }
}
