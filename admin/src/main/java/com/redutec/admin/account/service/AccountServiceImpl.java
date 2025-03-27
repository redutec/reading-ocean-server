package com.redutec.admin.account.service;

import com.redutec.admin.account.dto.AccountDto;
import com.redutec.core.repository.ActAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final ActAccountRepository actAccountRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AccountDto.AccountResponse> find(AccountDto.FindAccountRequest findAccountDto) {
        var actAccountEntityList = actAccountRepository.findAll();
        return actAccountEntityList.stream()
                .map(account -> AccountDto.AccountResponse.builder()
                        .accountNo(account.getAccountNo())
                        .accountId(account.getAccountId())
                        .build())
                .toList();
    }
}
