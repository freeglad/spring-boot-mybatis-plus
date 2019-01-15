package com.baomidou.springboot.test.ctrl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.springboot.Application;
import com.baomidou.springboot.test.ctrl.base.TestBase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class SimpleTest {

    public static void main(String[] args) {
        String user = null;
        System.out.println("is String: " + (user instanceof String));
    }

}
