package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import com.nowcoder.community.util.CommunityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello Spring Boot!";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String getData() {
        return alphaService.find();
    }

    // 底层的机制
    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response) {
        // 获取请求数据
        // 请求行：请求方法
        System.out.println(request.getMethod());
        // 请求行：URL协议
        System.out.println(request.getServletPath());
        // 请求头
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + " : " + value);
        }
        // 请求体
        System.out.println(request.getParameter("code"));

        // 返回响应数据
        response.setContentType("text/html;charset=utf-8");
        try (
                PrintWriter printWriter = response.getWriter();
        ) {
            printWriter.write("<h1>牛客网<h1>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 方便的方法
    // GET请求
    // /students?current=1&limit=20
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents (
        @RequestParam(name = "current", required = false, defaultValue = "1") int current,
        @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    // /students/101
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent (@PathVariable("id") int id) {
        System.out.println(id);
        return "a student";
    }

    // 响应html数据
    // 不加@ResponseBody表示返回的是一个html
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("name", "Loveyoyo");
        modelAndView.addObject("age", "19");
        modelAndView.setViewName("/demo/view");
        return modelAndView;
    }

    // 另一种方法
    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model) {
        model.addAttribute("name", "haust");
        model.addAttribute("age", "70");
        return "/demo/view";
    }

    // 响应json数据（异步请求）
    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody
    // 看注解及返回类型自动转换成json数据
    public Map<String, Object> getEmp() {
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "Loveyoyo");
        emp.put("age", 19);
        emp.put("salary", 30000.0);
        return emp;
    }

    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getEmps() {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "Loveyoyo");
        emp.put("age", 19);
        emp.put("salary", 30000.0);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "haha");
        emp.put("age", 19);
        emp.put("salary", 29999.0);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "清欢");
        emp.put("age", 20);
        emp.put("salary", 30001.0);
        list.add(emp);

        return list;
    }


    // POST请求
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return "save success!";
    }

    // ajax示例
    @RequestMapping(path = "/ajax", method = RequestMethod.GET)
    @ResponseBody
    public String testAjax(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return CommunityUtil.getJSONString(0, "操作成功！");
    }

}