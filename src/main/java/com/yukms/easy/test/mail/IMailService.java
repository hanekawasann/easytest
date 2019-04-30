package com.yukms.easy.test.mail;

/**
 * @author yukms 763803382@qq.com 2019/4/29 14:45
 */
public interface IMailService {
    boolean sendMail(long userId, String content);
}
