package com.instuid.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.instuid.common.Constants;
import com.instuid.common.Result;
import com.instuid.entity.Student;
import com.instuid.entity.Teacher;
import com.instuid.form.RuleForm;
import com.instuid.service.IAdminService;
import com.instuid.service.IStudentService;
import com.instuid.service.ITeacherService;
import com.instuid.utils.ResultVOUtil;
import com.instuid.vo.ResultVO;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class IAdminController {

    @Resource
    private IAdminService adminService;
    @Resource
    private ITeacherService teacherService;
    @Resource
    private IStudentService studentService;

    @GetMapping("/login")
    public Result Login(RuleForm ruleForm){
        Result result = adminService.login(ruleForm);
        if (result.getCode().equals(Constants.CODE_200) ){
            result.setCode("200");
            return result;
        } else if (result.getCode().equals(Constants.CODE_400) ) {
            return result;
        }
        result = teacherService.login(ruleForm);
        if (result.getCode().equals(Constants.CODE_200)){
            result.setCode("201");
            return result;
        }else if (result.getCode().equals(Constants.CODE_400) ) {
            return result;
        }
        result = studentService.login(ruleForm);
        if (result.getCode().equals(Constants.CODE_200)){
            result.setCode("202");
            return result;
        }else if (result.getCode().equals(Constants.CODE_400) ) {
            return result;
        }




        result = Result.error("-1","账号不存在");

        return result;
    }

    /**
     * excel 导入教师信息
     * @param file
     * @throws Exception
     */
    @PostMapping("/tea/import")
    public ResultVO impTea(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
        //        List<User> list = reader.readAll(User.class);

        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<Teacher> teachers = CollUtil.newArrayList();
        for (List<Object> row : list) {
            Teacher teacher = new Teacher();
            teacher.setTnum(row.get(0).toString());
            teacher.setTpwd(row.get(1).toString());
            teacher.setTname(row.get(2).toString());
            teacher.setEmail(row.get(3).toString());
            teacher.setPhone(row.get(4).toString());
            teacher.setDep(row.get(5).toString());
            teacher.setRole(row.get(6).toString());
            teacher.setAvailable(5);
            teachers.add(teacher);
        }
        teacherService.saveBatch(teachers);
        return ResultVOUtil.success("导入成功");
    }



    /**
     * excel 导入学生信息
     * @param file
     * @throws Exception
     */
    @PostMapping("/stu/import")
    public ResultVO impStu(@RequestParam MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
        //        List<User> list = reader.readAll(User.class);

        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<Student> students = CollUtil.newArrayList();
        for (List<Object> row : list) {
            Student student = new Student();
            student.setStudentId(row.get(0).toString());
            student.setSpwd(row.get(1).toString());
            student.setSname(row.get(2).toString());
            student.setDep(row.get(3).toString());
            student.setSclass(row.get(4).toString());
            student.setRole(row.get(5).toString());
            students.add(student);
        }
        studentService.saveBatch(students);
        return ResultVOUtil.success("导入成功");
    }







}
