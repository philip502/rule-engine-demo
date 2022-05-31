package com.ikong.demo.ql;

import com.ikong.demo.ql.entity.Order;
import com.ikong.demo.ql.entity.User;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName QLExpressionApplication
 * @Description: TODO
 * @Author ikong
 * @Date 2022/5/30
 * @Version V1.0
 **/
public class QLExpressionApplication {

    private static ExpressRunner runner = new ExpressRunner();

    private static IExpressContext<String, Object> expressContext = new DefaultContext<String, Object>();

    public static void main(String[] args) throws Exception {

        String expression = "if (o.amout > 1000) return 1000;"
                + " if (o.amout > 500  && o.amout <= 1000) return 500 ;"
                + " if (o.amout > 100  && o.amout <= 500) return 100 ;"
                + " if (o.amout <= 100 ) return 0 ;";


        List<Order> orderList = getInitData();
        int count = 200000;
        long now = System.currentTimeMillis();
        for (int j = 0; j < count; j++) {
            for (int i = 0; i < orderList.size(); i++) {
                Order order = orderList.get(i);
                String ret = execute(order, expression);
                if (!StringUtils.isEmpty(ret)) {
                    order.setScore(Integer.valueOf(ret));
                }

            }
        }
        long cost = System.currentTimeMillis() - now;
        System.out.println("java程序执行:" + count + "次，耗时：" + cost + "ms");
    }

    public static String execute(Object obj, String expression) throws Exception {
        expressContext.put("o", obj);
        List<String> errorInfo = new ArrayList<String>();
        Object resultObj = runner.execute(expression, expressContext, errorInfo, true, false);
        return null == resultObj ? "" : resultObj.toString();
    }


    private static List<Order> getInitData() throws Exception {
        List<Order> orderList = new ArrayList<Order>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        {
            Order order = new Order();
            order.setAmout(80);
            order.setBookingDate(df.parse("2015-07-01"));
            User user = new User();
            user.setLevel(1);
            user.setName("Name1");
            order.setUser(user);
            orderList.add(order);
        }
        {
            Order order = new Order();
            order.setAmout(200);
            order.setBookingDate(df.parse("2015-07-02"));
            User user = new User();
            user.setLevel(2);
            user.setName("Name2");
            order.setUser(user);
            orderList.add(order);
        }
        {
            Order order = new Order();
            order.setAmout(800);
            order.setBookingDate(df.parse("2015-07-03"));
            User user = new User();
            user.setLevel(3);
            user.setName("Name3");
            order.setUser(user);
            orderList.add(order);
        }
        {
            Order order = new Order();
            order.setAmout(1500);
            order.setBookingDate(df.parse("2015-07-04"));
            User user = new User();
            user.setLevel(4);
            user.setName("Name4");
            order.setUser(user);
            orderList.add(order);
        }
        return orderList;
    }

}
