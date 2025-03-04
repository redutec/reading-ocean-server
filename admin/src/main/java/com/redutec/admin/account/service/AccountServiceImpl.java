package com.redutec.admin.account.service;

import com.redutec.admin.account.dto.request.AccountSearchRequest;
import com.redutec.admin.account.dto.response.AccountSearchResponse;
import com.redutec.core.repository.ActAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final ActAccountRepository actAccountRepository;

    @Override
    public List<AccountSearchResponse> searchAccounts(AccountSearchRequest accountSearchRequest) {
        var actAccountEntityList = actAccountRepository.findAll();
        return actAccountEntityList.stream()
                .map(account -> AccountSearchResponse.builder()
                        .accountNo(account.getAccountNo())
                        .accountId(account.getAccountId())
                        .build())
                .toList();
    }
}
