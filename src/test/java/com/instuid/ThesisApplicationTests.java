package com.instuid;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.instuid.entity.Student;
import com.instuid.mapper.StudentMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ThesisApplicationTests {
    @Resource
    StudentMapper studentMapper;

    @Test
    void contextLoads() {

    }
    @Test
    public void testPage() {
        System.out.println("----- selectPage method test ------");
        //分页参数
        Page<Student> page = Page.of(1,10);

        //queryWrapper组装查询where条件
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getSpwd,123456);
        studentMapper.selectPage(page,queryWrapper);
        page.getRecords().forEach(System.out::println);
    }


}
