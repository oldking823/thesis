package com.instuid.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.instuid.common.Result;
import com.instuid.entity.Student;
import com.instuid.form.RuleForm;
import com.instuid.mapper.StudentMapper;
import com.instuid.service.IStudentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yang
 * @since 2023-06-07
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {
    @Resource
    StudentMapper studentMapper;
    @Override
    public Result login(RuleForm ruleForm) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",ruleForm.getUsername());
        Student student = studentMapper.selectOne(queryWrapper);
        if (student != null){
            if (student.getSpwd().equals(ruleForm.getPassword())){
                return Result.success(student);
            }
            return Result.error("400","用户名与密码不匹配·");
        }else if (student == null){
            return Result.error("401","非学生账号");
        }

        return Result.error("400","用户名与密码不匹配·");
    }

    @Override
    public IPage<Student> getpage(int pageNum, int pageSize, QueryWrapper<Student> queryWrapper) {

        Page<Student> page = Page.of(pageNum,pageSize);
        studentMapper.selectPage(page, queryWrapper);

        return page;
    }


}
