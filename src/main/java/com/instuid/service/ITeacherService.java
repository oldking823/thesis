package com.instuid.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.instuid.common.Result;
import com.instuid.entity.Teacher;
import com.instuid.form.RuleForm;
import com.instuid.form.SearchForm;
import com.instuid.vo.PageVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yang
 * @since 2023-06-07
 */
public interface ITeacherService extends IService<Teacher> {

    Result login(RuleForm ruleForm);
    Page<Teacher> getpage(int pageNum, int pageSize, QueryWrapper<Teacher> queryWrapper);
}
