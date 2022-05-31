
package com.ikong.demo.groovy
import com.ikong.demo.groovy.entity.Order

public class OrderScore {

    private Order order;

    public void apply() {
        if (order.getAmout() <= 100) {
            order.setScore(0);
        } else if (order.getAmout() > 100 && order.getAmout() <= 500) {
            order.setScore(100);
        } else if (order.getAmout() > 500 && order.getAmout() <= 1000) {
            order.setScore(500);
        } else {
            order.setScore(1000);
        }

//        return "ikong--->" + order.getScore();
    }
}