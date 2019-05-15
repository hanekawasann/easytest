package com.yukms.easy.test.order;

import javax.annotation.Resource;

import com.yukms.easy.test.mail.IMailService;
import com.yukms.easy.test.user.IUserCheckService;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.VerificationsInOrder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yukms 763803382@qq.com 2019/4/30 16:58
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {
    @Tested
    @Resource
    private IOrderService orderService;

    @Test
    public void test_submitOrder(@Injectable IUserCheckService userCheckService, @Injectable IMailService mailService)
        throws Exception {
        new Expectations() {
            {
                userCheckService.check(123456L);
                result = true;
                mailService.sendMail(123456L, "下单成功");
                result = true;
            }
        };
        boolean isSuccess = orderService.submitOrder(123456L, 78910L);
        Assert.assertTrue(isSuccess);
        new VerificationsInOrder() {
            {
                userCheckService.check(123456L);
                times = 1;
                mailService.sendMail(123456L, "下单成功");
                times = 1;
            }
        };
    }
}