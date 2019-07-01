package com.yukms.easy.test.mail;

import org.springframework.stereotype.Service;

/**
 * @author yukms 763803382@qq.com 2019/4/30 16:40
 */
@Service
public class MailServiceImpl implements IMailService {
    @Override
    public boolean sendMail(long userId, String content) {
        return true;
        //throw new RuntimeException();
    }
}
