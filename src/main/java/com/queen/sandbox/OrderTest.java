package com.queen.sandbox;

import java.util.List;

public class OrderTest {

    private List<Order> orderList;
    private List<Order> emptyOrderList;

    public OrderTest(List<Order> orderList, List<Order> emptyOrderList) {
        this.orderList = orderList;
        this.emptyOrderList = emptyOrderList;
    }

    public int getMaxAndAdd() {
        int maxNumber = this.orderList.stream().mapToInt(Order::getSerialNumber).max().orElse(0);
        return maxNumber + 1;
    }

    public int getMaxAndAddEmpty() {
        int maxNumber = this.emptyOrderList.stream().mapToInt(Order::getSerialNumber).max().orElse(0);
        return maxNumber + 1;
    }
}
