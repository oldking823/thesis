package com.instuid.vo;

import lombok.Data;

@Data
public class StudentVo {

    /**
     * 学生ID
     */
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
     * 状态
     */
    private String status;

    /**
     * 指导教师ID
     */
    private Integer teacherId;

    /**
     * 指导教师名字
     */
    private String teacherName;

    /**
     * 论文ID
     */
    private Integer thesisId;

    /**
     * 论文题目
     */
    private String thesisName;

    /**
     * 学生权限
     */
    private String role;
}
