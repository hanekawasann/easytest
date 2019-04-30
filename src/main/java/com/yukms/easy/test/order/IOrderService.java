package com.yukms.easy.test.order;

/**
 * @author yukms 763803382@qq.com 2019/4/30 16:40
 */
public interface IOrderService {
    boolean submitOrder(long buyerId, long itemId) throws Exception;
}
