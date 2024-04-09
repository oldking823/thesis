package com.instuid.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yang
 * @since 2023-06-07
 */
@Data
  public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 评审表ID
     */
      @TableId(value = "id", type = IdType.AUTO)
        private Integer id;

      /**
     * 论文ID
     */
      private Integer thesisId;

      /**
     * 教师ID
     */
      private Integer teacherId;

      /**
     * 评审进度
     */
      private String progress;

      /**
     * 评审留言
     */
      private String comment;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime timestamp;


}
