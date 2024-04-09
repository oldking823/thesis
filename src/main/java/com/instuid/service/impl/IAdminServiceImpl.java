package com.instuid.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.instuid.common.Result;
import com.instuid.entity.Admin;
import com.instuid.form.RuleForm;
import com.instuid.mapper.IAdminMapper;
import com.instuid.service.IAdminService;
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
public class IAdminServiceImpl extends ServiceImpl<IAdminMapper, Admin> implements IAdminService {
    @Resource
    private IAdminMapper IAdminMapper;
    @Override
    public Result login(RuleForm ruleForm) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",ruleForm.getUsername());
        Admin admin1 = IAdminMapper.selectOne(queryWrapper);
        if (admin1 != null){
            if (admin1.getPassword().equals(ruleForm.getPassword())){
                return Result.success(admin1);
            }
            return Result.error("400","用户名与密码不匹配·");
        }else if (admin1 == null){
            return Result.error("401","非管理员账号");
        }

        return Result.error("400","用户名与密码不匹配·");
    }
}
