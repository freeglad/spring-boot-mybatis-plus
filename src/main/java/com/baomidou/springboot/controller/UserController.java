package com.baomidou.springboot.controller;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.springboot.entity.User;
import com.baomidou.springboot.entity.enums.AgeEnum;
import com.baomidou.springboot.entity.enums.PhoneEnum;
import com.baomidou.springboot.service.IUserService;

/**
 * 代码生成器，参考源码测试用例：
 * <p>
 * /mybatis-plus/src/test/java/com/baomidou/mybatisplus/test/generator/MysqlGenerator.java
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 分页 PAGE
     */
    @GetMapping("/test")
    public IPage<User> test() {
        return userService.page(new Page<User>(0L, 12L), null);
    }

    /**
     * AR 部分测试
     */
    @GetMapping("/test1")
    public IPage<User> test1() {
        User user = new User("testAr", AgeEnum.ONE, 1);
        System.err.println("删除所有：" + user.delete(null));
        user.setRole(111L);
        user.setTestDate(new Date());
        user.setPhone(PhoneEnum.CMCC);
        user.insert();
        System.err.println("查询插入结果：" + user.selectById().toString());
        user.setName("mybatis-plus-ar");
        System.err.println("更新：" + user.updateById());
        return user.selectPage(new Page<User>(0L, 12L), null);
    }

    /**
     * 增删改查 CRUD
     */
    @GetMapping("/test2")
    public User test2() {
        System.err.println("删除一条数据：" + userService.removeById(1L));
        System.err.println("deleteAll：" + userService.deleteAll());
        System.err.println("插入一条数据：" + userService.save(new User(1L, "张三", AgeEnum.TWO, 1)));
        User user = new User("张三", AgeEnum.TWO, 1);
        boolean result = userService.save(user);
        // 自动回写的ID
        Long id = user.getId();
        System.err.println("插入一条数据：" + result + ", 插入信息：" + user.toString());
        System.err.println("查询：" + userService.getById(id).toString());
        System.err.println("更新一条数据：" + userService.updateById(new User(1L, "三毛", AgeEnum.ONE, 1)));
        for (int i = 0; i < 5; ++i) {
            userService.save(new User(Long.valueOf(100 + i), "张三" + i, AgeEnum.ONE, 1));
        }
        IPage<User> userListPage = userService.page(new Page<User>(1, 5), new QueryWrapper<>(new User()));
        System.err.println("total=" + userListPage.getTotal() + ", current list size=" + userListPage.getRecords().size());
        return userService.getById(1L);
    }

    /**
     * 插入 OR 修改
     */
    @GetMapping("/test3")
    public User test3() {
//        User user = new User(1L, "王五", AgeEnum.ONE, 1);
//        user.setPhone(PhoneEnum.CT);
//        userService.saveOrUpdate(user);

        User user1 = new User();
        user1.setName("test1");
        userService.save(user1);
        System.out.println(user1.getId());

        User user = new User();
        user.setId(1L);
        user.setName("update");
        userService.updateById(user);
        return userService.getById(1L);
    }


    /**
     * 测试实体注解注入条件 LIKE 查询
     */
    @GetMapping("/like")
    public Object like() {
        JSONObject result = new JSONObject();
        User user = new User();
        user.setName("三");
        result.put("result", userService.list(new QueryWrapper<>(user)));
        return result;
    }

    @GetMapping("/add")
    public Object addUser() {
        User user = new User("张三'特殊`符号", AgeEnum.TWO, 1);
        user.setPhone(PhoneEnum.CUCC);
        JSONObject result = new JSONObject();
        result.put("result", userService.save(user));
        return result;
    }

    @GetMapping("/selectsql")
    public Object getUserBySql() {
        JSONObject result = new JSONObject();
        result.put("records", userService.selectListBySQL());
        return result;
    }

    /**
     * 7、分页 size 一页显示数量  current 当前页码
     * 方式一：http://localhost:8080/user/page?size=1&current=1<br>
     * 方式二：http://localhost:8080/user/pagehelper?size=1&current=1<br>
     */

    // 参数模式分页
    @GetMapping("/page")
    public Object page(Page page) {
        return userService.page(page, null);
    }

//    // ThreadLocal 模式分页
//    @GetMapping("/pagehelper")
//    public Object pagehelper(Page page) {
//        PageHelper.setPagination(page);
//        page.setRecords(userService.selectList(null));
//        page.setTotal(PageHelper.freeTotal());//获取总数并释放资源 也可以 PageHelper.getTotal()
//        return page;
//    }


    /**
     * 测试事物
     * http://localhost:8080/user/test_transactional<br>
     * 访问如下并未发现插入数据说明事物可靠！！<br>
     * http://localhost:8080/user/test<br>
     * <br>
     * 启动  Application 加上 @EnableTransactionManagement 注解其实可无默认貌似就开启了<br>
     * 需要事物的方法加上 @Transactional 必须的哦！！
     */
    @Transactional
    @GetMapping("/test_transactional")
    public void testTransactional() {
        userService.save(new User(1000L, "测试事物", AgeEnum.ONE, 3));
        System.out.println(" 这里手动抛出异常，自动回滚数据");
        throw new RuntimeException();
    }
}
