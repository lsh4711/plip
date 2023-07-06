package com.server.domain.record.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.domain.member.entity.Member;
import com.server.domain.member.service.MemberService;
import com.server.domain.record.entity.Record;
import com.server.domain.record.repository.RecordRepository;
import com.server.global.exception.BusinessLogicException;
import com.server.global.exception.ExceptionCode;
import com.server.global.utils.CustomBeanUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    private final MemberService memberService;

    private final CustomBeanUtils<Record> beanUtils;


    //여행일지 등록
    @Transactional
    public Record createRecord(Record record) {
        Member member = authenticationMember();
        record.setMember(member);
        return recordRepository.save(record);
    }

    //여행일지 수정
    @Transactional
    public Record updateRecord(Record record) {
        Record foundRecord = findRecord(record.getRecordId());

        if(authenticationMember().getMemberId()!=foundRecord.getMember().getMemberId()){
            throw new BusinessLogicException(ExceptionCode.CANNOT_CHANGE_RECORD);
        }

        Record updatedRecord = beanUtils.copyNonNullProperties(record, foundRecord);

        return recordRepository.save(updatedRecord);
    }


    //여행일지 아이디로 여행일지 하나 조회(상세 페이지)
    public Record findRecord(long recordId) {
        Optional<Record> optionalReecord = recordRepository.findById(recordId);
        Record findRecord = optionalReecord.orElseThrow(
            () -> new BusinessLogicException(ExceptionCode.RECORD_NOT_FOUND));

        return findRecord;
    }

    //회원 아이디로 전체 여행일지 조회
    public Page<Record> findAllRecords(int page, int size) {
        Member member = authenticationMember();
        Long memberId = member.getMemberId();
        System.out.println(memberId);
        return recordRepository.findByMemberMemberId(PageRequest.of(page, size, Sort.Direction.DESC, "modifiedAt"),memberId);
    }


    //여행일지 삭제
    public void deleteRecord(long recordId) {
        Record foundRecord = findRecord(recordId);

        if(authenticationMember().getMemberId()!=foundRecord.getMember().getMemberId()){
            throw new BusinessLogicException(ExceptionCode.CANNOT_CHANGE_RECORD);
        }

        recordRepository.delete(foundRecord);
    }

    //등록된 사용자인지 확인
    private Member authenticationMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //현재 로그인한 사용자 이메일
        String username = (String) authentication.getPrincipal();

        // 로그인한 ID(이매일)로 Member를 찾아서 반환
        return memberService.findMemberByEmail(username);
    }
}
