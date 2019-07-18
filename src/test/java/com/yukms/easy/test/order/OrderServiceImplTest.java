package com.yukms.easy.test.order;

import javax.annotation.Resource;

import com.yukms.easy.test.EasyTestApplicationTests;
import com.yukms.easy.test.mock.annotation.EasyTestMock;
import org.junit.Test;

/**
 * @author yukms 763803382@qq.com 2019/4/30 16:58
 */
public class OrderServiceImplTest extends EasyTestApplicationTests {
    @Resource
    private IOrderService orderService;

    @Test
    public void test_submitOrder() throws Exception {
        orderService.submitOrder(123456L, 78910L);
    }

    @Test(expected = RuntimeException.class)
    public void test_submitOrder_exception() throws Exception {
        orderService.submitOrder(123456L, 78910L);
    }
}