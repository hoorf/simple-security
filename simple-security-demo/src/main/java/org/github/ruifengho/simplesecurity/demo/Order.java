package org.github.ruifengho.simplesecurity.demo;

public class Order {

    private String orderNo;

    private String name;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNo='" + orderNo + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
