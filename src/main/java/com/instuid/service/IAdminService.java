package com.instuid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.instuid.common.Result;
import com.instuid.entity.Admin;
import com.instuid.form.RuleForm;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yang
 * @since 2023-06-07
 */
public interface IAdminService extends IService<Admin> {
    public Result login(RuleForm ruleForm);

}
