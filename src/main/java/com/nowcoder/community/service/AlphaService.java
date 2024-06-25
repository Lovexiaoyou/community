package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
// @Scope("prototype") 多个实例，很少用
public class AlphaService {

    @Autowired
    public AlphaDao alphaDao;

    public AlphaService() {
//        System.out.println("实例化AlphaService");
    }

    @PostConstruct
    public void Init() {
//        System.out.println("初始化AlphaService");
    }

    @PreDestroy
    public void Destroy() {
//        System.out.println("销毁AlphaService");
    }

    public String find() {
        return alphaDao.select();
    }
}
