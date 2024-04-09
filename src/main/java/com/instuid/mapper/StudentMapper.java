package com.instuid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.instuid.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yang
 * @since 2023-06-07
 */
@Mapper
@Repository
public interface StudentMapper extends BaseMapper<Student> {

}
