package com.instuid.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.instuid.entity.Review;
import com.instuid.entity.Student;
import com.instuid.mapper.ReviewMapper;
import com.instuid.service.IReviewService;
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
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements IReviewService {
    @Resource
    ReviewMapper reviewMapper;

    @Override
    public IPage<Review> getpage(int pageNum, int pageSize, QueryWrapper<Review> queryWrapper) {

        Page<Review> page = Page.of(pageNum,pageSize);

        reviewMapper.selectPage(page, queryWrapper);

        return page;
    }
}
