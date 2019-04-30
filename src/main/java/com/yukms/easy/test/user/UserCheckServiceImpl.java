package com.yukms.easy.test.user;

import org.springframework.stereotype.Service;

/**
 * @author yukms 763803382@qq.com 2019/4/30 16:42
 */
@Service
public class UserCheckServiceImpl implements IUserCheckService {
    @Override
    public boolean check(long userId) {
        return false;
    }
}
