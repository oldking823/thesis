package com.instuid.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.instuid.common.Result;
import com.instuid.entity.Student;
import com.instuid.form.RuleForm;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yang
 * @since 2023-06-07
 */
public interface IStudentService extends IService<Student> {


    public Result login(RuleForm ruleForm);

    IPage<Student> getpage(int pageNum, int pageSize, QueryWrapper<Student> queryWrapper);
}
