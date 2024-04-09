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
public class Thesis implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 论文ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 论文标题
     */
    private String title;

    /**
     * 论文内容
     */
    private String content;
    /**
     * 文件路径
     */
    private String path;

    /**
     * 论文状态
     */
    private String status;

    /**
     * 指导教师ID
     */
    private Integer teacherId;

    /**
     * 学生id
     */
    private Integer studentId;

}
