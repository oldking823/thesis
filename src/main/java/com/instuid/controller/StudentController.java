package com.instuid.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.instuid.common.Result;
import com.instuid.entity.Student;
import com.instuid.entity.Teacher;
import com.instuid.entity.Thesis;
import com.instuid.form.RuleForm;
import com.instuid.form.SearchForm;
import com.instuid.service.IStudentService;
import com.instuid.service.ITeacherService;
import com.instuid.service.IThesisService;
import com.instuid.utils.ResultVOUtil;
import com.instuid.vo.ResultVO;
import com.instuid.vo.StudentVo;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/student")
public class StudentController {

    @Resource
    private IStudentService studentService;
    @Resource
    private ITeacherService teacherService;
    @Resource
    private IThesisService thesisService;

    //登录


    // 新增或者更新
    @PostMapping
    public ResultVO save(@RequestBody Student student) {
        if (studentService.save(student)){
            return ResultVOUtil.success("保存成功");
        }
        return ResultVOUtil.fail();
    }

    @DeleteMapping("/{id}")
    public ResultVO delete(@PathVariable Integer id) {
        if (studentService.removeById(id)){
            return ResultVOUtil.success("删除成功");
        }
        return ResultVOUtil.fail();
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return studentService.removeByIds(ids);
    }

    @GetMapping
    public List<Student> findAll() {
        return studentService.list();
    }

    @PutMapping
    public ResultVO update(@RequestBody Student student){
        if (this.studentService.updateById(student)){

            return ResultVOUtil.success("修改成功");
        }
        return ResultVOUtil.fail();
    }

    @GetMapping("/{id}")
    public ResultVO findById(@PathVariable Integer id) {
        return ResultVOUtil.success(this.studentService.getById(id));
    }

    @GetMapping("/page/{pageNum}/{pageSize}")
    public Result findPage(@PathVariable Integer pageNum,
                           @PathVariable Integer pageSize) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        IPage<Student> page = studentService.getpage(pageNum, pageSize, queryWrapper);


        List<Student> studentsList = page.getRecords();

        List<StudentVo> studentVoList = new ArrayList<>();
        for (Student student : studentsList) {
            StudentVo studentVo = new StudentVo();
            BeanUtils.copyProperties(student, studentVo);
            Teacher teacher = teacherService.getById(student.getTeacherId());
            Thesis thesis = thesisService.getById(student.getThesisId());
            if (teacher != null) {
                studentVo.setTeacherName(teacher.getTname());
            } else {
                studentVo.setTeacherName("未选择");
            }
            if (thesis != null) {
                studentVo.setThesisName(thesis.getTitle());
                studentVo.setStatus(thesis.getStatus());
            } else {
                studentVo.setStatus("未提交");
                studentVo.setThesisName("还未提交");
            }
            studentVoList.add(studentVo);


        }
        IPage<StudentVo> page1 = new Page<>();
        BeanUtils.copyProperties(page, page1);
        page1.setRecords(studentVoList);
        ;
        return Result.success(page1);

//        return Result.success(page1);
    }
    @GetMapping("/mypage/{pageNum}/{pageSize}")
    public Result findmyPage(@PathVariable Integer pageNum,
                           @PathVariable Integer pageSize,
                           @RequestParam Integer teacherId) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id",teacherId);
        queryWrapper.orderByDesc("id");
        IPage<Student> page =  studentService.getpage(pageNum, pageSize, queryWrapper);
        List<Student> studentsList = page.getRecords();

        List<StudentVo> studentVoList = new ArrayList<>();
        for (Student student : studentsList) {
            StudentVo studentVo = new StudentVo();
            BeanUtils.copyProperties(student, studentVo);
            Teacher teacher = teacherService.getById(student.getTeacherId());
            Thesis thesis = thesisService.getById(student.getThesisId());
            if (teacher != null) {
                studentVo.setTeacherName(teacher.getTname());
            } else {
                studentVo.setTeacherName("未选择");
            }
            if (thesis != null) {
                studentVo.setThesisName(thesis.getTitle());
                studentVo.setStatus(thesis.getStatus());
            } else {
                studentVo.setStatus("未提交");
                studentVo.setThesisName("还未提交");
            }
            studentVoList.add(studentVo);


        }
        IPage<StudentVo> page1 = new Page<>();
        BeanUtils.copyProperties(page, page1);
        page1.setRecords(studentVoList);
        ;
        return Result.success(page1);

//        return Result.success(page1);
    }
    @GetMapping("/login")
    public Result Login(RuleForm ruleForm){




        return studentService.login(ruleForm);
    }
    @GetMapping("/search")
    public ResultVO search(SearchForm searchForm){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (searchForm.getKey().equals("number")){
            queryWrapper.like("student_id",searchForm.getValue());
        } else if (searchForm.getKey().equals("name")) {
            queryWrapper.like("sname",searchForm.getValue());
        }
        IPage<Student> page = studentService.getpage(searchForm.getPage(), searchForm.getSize(), queryWrapper);
        List<Student> studentsList = page.getRecords();

        List<StudentVo> studentVoList = new ArrayList<>();
        for (Student student : studentsList) {
            StudentVo studentVo = new StudentVo();
            BeanUtils.copyProperties(student, studentVo);
            Teacher teacher = teacherService.getById(student.getTeacherId());
            Thesis thesis = thesisService.getById(student.getThesisId());
            if (teacher != null) {
                studentVo.setTeacherName(teacher.getTname());
            } else {
                studentVo.setTeacherName("未选择");
            }
            if (thesis != null) {
                studentVo.setThesisName(thesis.getTitle());
            } else {
                studentVo.setThesisName("还未提交");
            }
            studentVoList.add(studentVo);


        }
        IPage<StudentVo> page1 = new Page<>();
        BeanUtils.copyProperties(page, page1);
        page1.setRecords(studentVoList);
        return ResultVOUtil.success(page1);
    }
}

