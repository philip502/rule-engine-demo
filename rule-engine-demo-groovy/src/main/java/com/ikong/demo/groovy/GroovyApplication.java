package com.ikong.demo.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName GroovyApplication
 * @Description: TODO
 * @Author ikong
 * @Date 2022/5/30
 * @Version V1.0
 **/
public class GroovyApplication {

    private static String BASIC_FILE_DIR_PATH = "/Users/luyifeng5/ikong/demo/rule-engine-demo/rule-engine-demo-groovy/";

    private static String GROOVY_FILE_DIR_PATH = BASIC_FILE_DIR_PATH + "src/main/resources/groovy/";

    private static String GROOVY_JAVA_SCRIPT = GROOVY_FILE_DIR_PATH + "test01_java.groovy";

    private static String GROOVY_G_SCRIPT = "test02_script.groovy";

    private static String GROOVY_P_SCRIPT = "test03_param.groovy";

    public static void main(String[] args) throws ResourceException, ScriptException, IOException, IllegalAccessException, InstantiationException {
        executeJava();//groovy 执行java编写的规则代码
        executeGroovyScript(); //groovy 执行groovy脚本编写的规则代码
        executeGroovyParamScript(); //groovy 执行groovy脚本编写的传参的规则代码
    }


    private static void executeJava() throws IOException, IllegalAccessException, InstantiationException {
        //方式一调用groovy文件
        ClassLoader parent = GroovyApplication.class.getClassLoader();
        GroovyClassLoader loader = new GroovyClassLoader(parent);
        Class groovyClass = loader.parseClass(new File(GROOVY_JAVA_SCRIPT));

        //得到groovy对象
        GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
        //执行对象的test方法，并传参数－"id---1"
        String result = (String) groovyObject.invokeMethod("test", "id---1");
        System.out.println("result--------->" + result);
    }

    private static void executeGroovyScript() throws IOException, ResourceException, ScriptException {
        //方式二调用groovy文件，找到groovy脚本所在文件夹
        GroovyScriptEngine engine = new GroovyScriptEngine(GROOVY_FILE_DIR_PATH);
        //得到Script对象
        Script script = engine.createScript(GROOVY_G_SCRIPT, new Binding());
        //执行Script对象的test方法，并传参数－"id---1"
        String result = (String) script.invokeMethod("test", "id----2");
        System.out.println("result--------->" + result);
    }

    private static void executeGroovyParamScript() throws IOException, ResourceException, ScriptException {

        GroovyScriptEngine engine = new GroovyScriptEngine(GROOVY_FILE_DIR_PATH);

        //方式三调用groovy文件
        Binding binding = new Binding();
        //封装参数
        binding.setVariable("id", "id---3");
        binding.setVariable("name", "ikong");

        //执行test03_param.groovy脚本
        engine.run(GROOVY_P_SCRIPT, binding);
        //返回output
        String result = binding.getVariable("output").toString();
        System.out.println("result--------->" + result);
    }
}
