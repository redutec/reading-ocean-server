package com.redutec.admin.student.service;

import com.redutec.admin.student.dto.StudentDto;
import com.redutec.core.repository.ActAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final ActAccountRepository actAccountRepository;

    @Override
    @Transactional(readOnly = true)
    public List<StudentDto.AccountResponse> find(StudentDto.FindAccountRequest findAccountDto) {
        var actAccountEntityList = actAccountRepository.findAll();
        return actAccountEntityList.stream()
                .map(account -> StudentDto.AccountResponse.builder()
                        .accountNo(account.getAccountNo())
                        .accountId(account.getAccountId())
                        .build())
                .toList();
    }
}
