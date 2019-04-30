package com.yukms.easy.test.order;

import javax.annotation.Resource;

import com.yukms.easy.test.mail.IMailService;
import com.yukms.easy.test.user.IUserCheckService;
import org.springframework.stereotype.Service;

/**
 * @author yukms 763803382@qq.com 2019/4/30 16:38:39
 */
@Service
public class OrderServiceImpl implements IOrderService {
    @Resource
    private IMailService mailService;
    @Resource
    private IUserCheckService userCheckService;

    public boolean submitOrder(long buyerId, long itemId) {
        if (!userCheckService.check(buyerId)) {
            return false;
        }
        // ...
        return mailService.sendMail(buyerId, "下单成功");
    }
}
