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
  public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 学生ID
     */
       @TableId(value = "id", type = IdType.AUTO)
        private Integer id;

      /**
     * 学号
     */
      private String studentId;

      /**
     * 密码
     */
      private String spwd;

      /**
     * 姓名
     */
      private String sname;

      /**
     * 专业
     */
      private String dep;

      /**
     * 班级
     */
      private String sclass;

      /**
     * 指导教师ID
     */
      private Integer teacherId;

      /**
     * 论文ID
     */
      private Integer thesisId;

      /**
     * 学生权限
     */
      private String role;


}
