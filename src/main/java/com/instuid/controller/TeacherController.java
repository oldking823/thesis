package com.instuid.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.instuid.common.Result;
import com.instuid.entity.Student;
import com.instuid.entity.Teacher;
import com.instuid.form.RuleForm;
import com.instuid.form.SearchForm;
import com.instuid.service.IStudentService;
import com.instuid.service.ITeacherService;
import com.instuid.utils.ResultVOUtil;
import com.instuid.vo.ResultVO;
import com.instuid.vo.TeacherVo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/teacher")
public class TeacherController {

    @Resource
    private ITeacherService teacherService;
    @Resource
    private IStudentService studentService;

    //登录


    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Teacher teacher) {
        teacher.setRole("teacher");
        if (teacher.getTpwd() == null){
            teacher.setTpwd("123123");
        }

        if (teacherService.saveOrUpdate(teacher)){
            return Result.success();
        }
        return Result.error();
    }

    @DeleteMapping("/{id}")
    public ResultVO delete(@PathVariable Integer id) {
        if (teacherService.removeById(id)){
            return ResultVOUtil.success("删除成功");
        }
        return ResultVOUtil.fail();
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return teacherService.removeByIds(ids);
    }

    @GetMapping
    public List<Teacher> findAll() {
        return teacherService.list();
    }
    @GetMapping("/login")
    public Result Login(RuleForm ruleForm){




        return teacherService.login(ruleForm);
    }

    @GetMapping("/availableList")
    public ResultVO availableList(@RequestParam String dep) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dep",dep);
        queryWrapper.gt("available",0);
        List<Teacher> teacherList =this.teacherService.list(queryWrapper);

        return ResultVOUtil.success(teacherList);
    }
    @PutMapping()
    public ResultVO update(@RequestBody Teacher teacher){
        if (this.teacherService.updateById(teacher)){
            return ResultVOUtil.success("修改成功");
        }
        return ResultVOUtil.fail();
    }

    @GetMapping("/{id}")
    public ResultVO findOne(@PathVariable Integer id) {

        return ResultVOUtil.success(teacherService.getById(id));
    }

    @GetMapping("/page/{pageNum}/{pageSize}")
    public ResultVO findPage(@PathVariable Integer pageNum,
                           @PathVariable Integer pageSize) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return ResultVOUtil.success(teacherService.getpage(pageNum, pageSize, queryWrapper));
    }
    @GetMapping("/mypage/{pageNum}/{pageSize}")
    public ResultVO findmyPage(@PathVariable Integer pageNum,
                             @PathVariable Integer pageSize,
                               @RequestParam String dep) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("dep",dep);
        return ResultVOUtil.success(teacherService.getpage(pageNum, pageSize, queryWrapper));
    }

    @GetMapping("/search")
    public ResultVO search(SearchForm searchForm){

        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (searchForm.getDep() != null){
            queryWrapper.eq("dep",searchForm.getDep());
        }
        if (searchForm.getKey().equals("number")){
            queryWrapper.like("tnum",searchForm.getValue());
        } else if (searchForm.getKey().equals("name")) {
            queryWrapper.like("tname",searchForm.getValue());
        }
        return ResultVOUtil.success(teacherService.getpage(searchForm.getPage(), searchForm.getSize(), queryWrapper));

    }
    @PostMapping("/select")
    public ResultVO select(@RequestBody TeacherVo teacherData){
        Integer NewTid = teacherData.getNewTid();
        Integer OldTid = teacherData.getOldTid();
        Integer studetId = teacherData.getStudentId();
        System.out.println(teacherData.toString());
        Student student = studentService.getById(studetId);
        student.setTeacherId(NewTid);
        studentService.updateById(student);


        Teacher teacher = teacherService.getById(NewTid);
        teacher.setAvailable(teacher.getAvailable()-1);
        teacherService.updateById(teacher);
        if (OldTid != 0){


            Teacher teacher1 = teacherService.getById(OldTid);
            teacher1.setAvailable(teacher1.getAvailable()+1);
            teacherService.updateById(teacher1);

        }
        return ResultVOUtil.success(student);
    }
}

