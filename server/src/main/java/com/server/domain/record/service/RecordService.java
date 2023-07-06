package com.server.domain.record.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.server.domain.member.entity.Member;
import com.server.domain.member.service.MemberService;
import com.server.domain.record.entity.Record;
import com.server.domain.record.repository.RecordRepository;
import com.server.global.exception.BusinessLogicException;
import com.server.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    private final MemberService memberService;


    public Record createRecord(Record record) {
        Member member = authenticationMember();
        record.setMember(member);
        return recordRepository.save(record);
    }

    public Record findRecord(long recordId) {
        Optional<Record> optionalReecord = recordRepository.findById(recordId);
        Record findRecord = optionalReecord.orElseThrow(
            () -> new BusinessLogicException(ExceptionCode.RECORD_NOT_FOUND));

        return findRecord;
    }

    //아이디로 여행일지 조회
    public Page<Record> findAllRecords(int page, int size) {
        Member member = authenticationMember();
        Long memberId = member.getMemberId();
        return recordRepository.findByMemberMemberId(PageRequest.of(page, size, Sort.Direction.DESC, "modifiedAt"),memberId);
    }

    //등록된 사용자인지 확인
    private Member authenticationMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //현재 로그인한 사용자 이메일
        String username = (String) authentication.getPrincipal();

        // 로그인한 ID(이매일)로 Member를 찾아서 반환
        return memberService.findVerifiedMember(username);
    }


}
