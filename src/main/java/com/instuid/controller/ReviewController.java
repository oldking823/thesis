package com.instuid.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.instuid.entity.Review;
import com.instuid.service.IReviewService;
import com.instuid.service.IThesisService;
import com.instuid.utils.ResultVOUtil;
import com.instuid.vo.ResultVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yang
 * @since 2023-06-07
 */
@CrossOrigin
@RestController
@RequestMapping("/review")
public class ReviewController {

    @Resource
    private IReviewService reviewService;

    // 新增或者更新


    // 通过id删除
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return reviewService.removeById(id);
    }

    //批量删除
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return reviewService.removeByIds(ids);
    }

    //查看所有
    @GetMapping
    public List<Review> findAll() {
        return reviewService.list();
    }

    //通过id查找
    @GetMapping("/{id}")
    public Review findOne(@PathVariable Integer id) {
        return reviewService.getById(id);
    }

    @GetMapping("getList/{thesisId}")
    public ResultVO getList(@PathVariable Integer thesisId){
        QueryWrapper<Review> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("thesis_id",thesisId);



        return ResultVOUtil.success(reviewService.list(queryWrapper));
    }
    @PostMapping
    public ResultVO update(@RequestBody Review review) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = now.format(formatter);

        LocalDateTime formattedDateTime = LocalDateTime.parse(formattedTimestamp, formatter);

        review.setTimestamp(formattedDateTime);
        if (this.reviewService.save(review)) {
            return ResultVOUtil.success("修改成功");
        }
        return ResultVOUtil.fail();
    }


    //分页显示
    @GetMapping("/page/{thesisId}/{pageNum}/{pageSize}")
    public ResultVO findPage(@PathVariable Integer thesisId,
            @PathVariable Integer pageNum,
                                 @PathVariable Integer pageSize) {
        QueryWrapper<Review> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("thesis_id",thesisId);
        return ResultVOUtil.success(reviewService.getpage(pageNum, pageSize, queryWrapper));


    }
}

