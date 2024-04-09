package com.instuid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author yang
 * @since 2023-06-07
 */
@Data
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 教师编号
     */

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 教师号（账号）
     */
    private String tnum;

    /**
     * 密码
     */
    private String tpwd;

    /**
     * 教师名
     */
    private String tname;

    /**
     * 学院
     */
    private String dep;

    /**
     * 教师邮箱
     */
    private String email;

    /**
     * 教师电话
     */
    private String phone;
    /**
     * 可选人数
     */
    private Integer available;

    /**
     * 教师权限
     */
    private String role;


}
