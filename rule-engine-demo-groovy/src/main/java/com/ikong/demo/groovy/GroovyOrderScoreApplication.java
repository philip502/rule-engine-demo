package com.ikong.demo.groovy;

import com.ikong.demo.groovy.entity.Order;
import com.ikong.demo.groovy.entity.User;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName GroovyApplication
 * @Description: TODO
 * @Author ikong
 * @Date 2022/5/30
 * @Version V1.0
 **/
public class GroovyOrderScoreApplication {

    private static String BASIC_FILE_DIR_PATH = "/Users/luyifeng5/ikong/demo/rule-engine-demo/rule-engine-demo-groovy/";

    private static String GROOVY_FILE_DIR_PATH = BASIC_FILE_DIR_PATH + "src/main/resources/groovy/";

    private static String GROOVY_ORDER01_SCRIPT = GROOVY_FILE_DIR_PATH + "order01_java.groovy";

    private static String GROOVY_G_SCRIPT = "test02_script.groovy";

    private static String GROOVY_P_SCRIPT = "test03_param.groovy";

    public static void main(String[] args) throws Exception {
        executeJava();//groovy 执行java编写的规则代码
    }


    private static void executeJava() throws Exception {

        //方式一调用groovy文件
        ClassLoader parent = GroovyOrderScoreApplication.class.getClassLoader();
        GroovyClassLoader loader = new GroovyClassLoader(parent);
        Class groovyClass = loader.parseClass(new File(GROOVY_ORDER01_SCRIPT));
        //得到groovy对象
        GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();

        List<Order> orderList = getInitData();

        int count = 200000;
        long now = System.currentTimeMillis();

        for (int j = 0; j < count; j++) {
            for (int i = 0; i < orderList.size(); i++) {
                Order order = orderList.get(i);
                groovyObject.setProperty("order", order);
                groovyObject.invokeMethod("apply", null);
            }
        }

        long cost = System.currentTimeMillis() - now;

        System.out.println("drools程序执行:" + count + "次，耗时：" + cost + "ms");

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
            order.setScore(111);
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
