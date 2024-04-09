package com.instuid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
  public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 管理员id
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 管理员账号
     */
      private String username;

      /**
     * 管理员密码
     */
      private String password;

      /**
     * 管理员姓名
     */
      private String name;

      /**
     * 管理员电话
     */
      private String phone;

      /**
     * 管理员邮箱
     */
      private String email;

      /**
     * 管理员权限
     */
      private String role;


}
