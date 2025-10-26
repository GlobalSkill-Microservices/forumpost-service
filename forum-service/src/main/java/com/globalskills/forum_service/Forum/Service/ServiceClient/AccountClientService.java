package com.globalskills.forum_service.Forum.Service.ServiceClient;

import com.globalskills.forum_service.Common.AccountDto;
import com.globalskills.forum_service.Common.Feign.AccountClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountClientService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AccountClient accountClient;

    public AccountDto fetchAccount(Long id){
        return accountClient.getAccountById(id);
    }
    public List<AccountDto> fetchListAccount(List<Long> ids){
        return accountClient.getAccountByIds(ids);
    }

}
