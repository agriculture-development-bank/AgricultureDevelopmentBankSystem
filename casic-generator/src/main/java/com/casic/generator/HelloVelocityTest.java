package com.casic.generator;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HelloVelocityTest {
    public static void main(String[] args) {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        // 载入（获取）模板对象
        Template t = ve.getTemplate("vm/Hellovelocity.vm");
        VelocityContext ctx = new VelocityContext();
        // 域对象加入参数值
        ctx.put("name", "测试");
        ctx.put("date", (new Date()).toString());
        // list集合
        List temp = new ArrayList();
        temp.add("1");
        temp.add("2");
        temp.add("3");
        ctx.put("list", temp);

        StringWriter sw = new StringWriter();
        t.merge(ctx, sw);

        System.out.println(sw.toString());

    }
}