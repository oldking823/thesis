package com.instuid.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.instuid.common.Result;
import com.instuid.entity.Admin;
import com.instuid.entity.Teacher;
import com.instuid.form.RuleForm;
import com.instuid.form.SearchForm;
import com.instuid.mapper.TeacherMapper;
import com.instuid.service.ITeacherService;
import com.instuid.vo.PageVo;
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
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {
    @Resource
    private TeacherMapper teacherMapper;
    @Override
    public Result login(RuleForm ruleForm) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tnum",ruleForm.getUsername());
        Teacher teacher = teacherMapper.selectOne(queryWrapper);
        if (teacher != null){
            if (teacher.getTpwd().equals(ruleForm.getPassword())){
                return Result.success(teacher);
            }
            return Result.error("400","用户名与密码不匹配·");
        }else if (teacher == null){
            return Result.error("401","非教师账号");
        }

        return Result.error("400","用户名与密码不匹配·");
    }

    @Override
    public Page<Teacher> getpage(int pageNum, int pageSize, QueryWrapper<Teacher> queryWrapper) {
        Page<Teacher> page = Page.of(pageNum,pageSize);
        teacherMapper.selectPage(page,queryWrapper);
        return page;
    }


}
