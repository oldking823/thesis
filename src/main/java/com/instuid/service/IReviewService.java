package com.instuid.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.instuid.entity.Review;
import com.instuid.entity.Student;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yang
 * @since 2023-06-07
 */
public interface IReviewService extends IService<Review> {
    IPage<Review> getpage(int pageNum, int pageSize, QueryWrapper<Review> queryWrapper);

}
