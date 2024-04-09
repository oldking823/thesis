package com.instuid.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.instuid.common.Result;
import com.instuid.entity.Student;
import com.instuid.entity.Thesis;
import com.instuid.service.IStudentService;
import com.instuid.service.IThesisService;
import com.instuid.utils.ResultVOUtil;
import com.instuid.vo.EchartVo;
import com.instuid.vo.ResultVO;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.buf.CharsetUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yang
 * @since 2023-06-07
 */
@CrossOrigin
@RestController
@RequestMapping("/thesis")
public class ThesisController {
    
@Resource
private IThesisService thesisService;
@Resource
private IStudentService studentService;
    @Value("${file.location}")
    String rootpath;

    // 新增或者更新


    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return thesisService.removeById(id);
        }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
            return thesisService.removeByIds(ids);
        }

    @GetMapping
    public List<Thesis> findAll() {
            return thesisService.list();
        }

    @GetMapping("/{id}")
    public Thesis findOne(@PathVariable Integer id) {
        return thesisService.getById(id);
        }

    @GetMapping("/page")
    public Page<Thesis> findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
        QueryWrapper<Thesis> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return thesisService.page(new Page<>(pageNum, pageSize), queryWrapper);
        }
    @PostMapping
    public ResultVO update(@RequestBody Thesis thesis){
        System.out.println(thesis.toString());
        Thesis thesis1 = this.thesisService.getById(thesis.getId());
        System.out.println("运行中断");
        thesis1.setStatus(thesis.getStatus());
        System.out.println(thesis1.toString());
        if (this.thesisService.updateById(thesis1)){
            return ResultVOUtil.success("论文状态更新成功");
        }else {
            return ResultVOUtil.fail();
        }
    }

    @GetMapping("/total")
    public Result total(){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("thesisId",null);
        int studentnum = (int)studentService.count(queryWrapper);
        int studentnums = (int)studentService.count();

    return Result.error();

    }
    @GetMapping("/student")
    public ResultVO searchStudent(){
        List<EchartVo> echartVoList = new ArrayList<>();
        EchartVo echartVo = new EchartVo();
        echartVo.setName("未提交");
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("thesis_id");

        echartVo.setValue((int)this.studentService.count(queryWrapper));
        echartVoList.add(echartVo);
        EchartVo echartVo1 = new EchartVo();
        echartVo1.setName("已提交");
        echartVo1.setValue((int)this.studentService.count()-echartVo.getValue());
        echartVoList.add(echartVo1);




        return ResultVOUtil.success(echartVoList);
    }
    @GetMapping("/thesis")
    public ResultVO searchThesis(){
        List<EchartVo> echartVoList = new ArrayList<>();
        EchartVo echartVo = new EchartVo();
        echartVo.setName("未提交");
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("thesis_id");
        echartVo.setValue((int)this.studentService.count(queryWrapper));
        echartVoList.add(echartVo);

        EchartVo echartVo1 = new EchartVo();
        echartVo1.setName("未批改");
        QueryWrapper<Thesis> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("status","未批改");
        echartVo1.setValue((int)this.thesisService.count(queryWrapper1));
        echartVoList.add(echartVo1);

        EchartVo echartVo2 = new EchartVo();
        echartVo2.setName("待修改");
        QueryWrapper<Thesis> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("status","待修改");
        echartVo2.setValue((int)this.thesisService.count(queryWrapper2));
        echartVoList.add(echartVo2);

        EchartVo echartVo3 = new EchartVo();
        echartVo3.setName("已通过");
        QueryWrapper<Thesis> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("status","已通过");
        echartVo3.setValue((int)this.thesisService.count(queryWrapper3));
        echartVoList.add(echartVo3);





        return ResultVOUtil.success(echartVoList);
    }
    @PostMapping("/upload")
    public ResultVO upload(@RequestParam MultipartFile file,
                           @RequestParam("Id") Integer Id,
                           @RequestParam("teacherId") Integer teacherId,
                           HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        Thesis thesis =  new Thesis();


        String filename = file.getOriginalFilename();



        thesis.setTitle(filename.split("\\.")[0]);
        System.out.println(filename);
        UUID uuid = UUID.randomUUID();
        filename = filename.split("\\.")[0]+uuid.toString()+"."+filename.split("\\.")[1];
        File filepath = new File(rootpath, filename);
        //路径不存在就创建一个路径
        if (!filepath.getParentFile().exists()) {
            filepath.getParentFile().mkdirs();
        }
        file.transferTo(new File(rootpath + File.separator + filename));
        System.out.println("文件上传完成");
        System.out.println(Id);

        thesis.setStatus("未批改");
        thesis.setPath(filename);

        thesis.setStudentId(Id);
        thesis.setTeacherId(teacherId);

        Student student = studentService.getById(Id);
        if(student.getThesisId() == null){
//            如果没有信息就保存，有就更新
            thesisService.save(thesis);
            student.setThesisId(thesis.getId());
        }else {
            thesis.setId(student.getThesisId());
            thesisService.updateById(thesis);

        }



        studentService.updateById(student);


        return ResultVOUtil.success("提交成功");
    }

    @GetMapping("/download/{thesisId}")
    public void download(@PathVariable String thesisId, HttpServletResponse response) throws IOException {
        Thesis thesis = this.thesisService.getById(thesisId);
        if (thesis.equals(null)){
            System.out.println("查询为空");

        }
        String fileName = thesis.getPath();

        // 设置文件名，根据业务需要替换成要下载的文件名
        if (fileName != null) {
            //设置文件路径
            String realPath = rootpath;
            File file = new File(realPath,fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                fileName = URLEncoder.encode(fileName, "UTF-8");
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);//设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("download success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }


//        InputStream inputStream = new FileInputStream(path);// 文件的存放路径
//        response.reset();
//        response.setContentType("application/octet-stream");
//        String filename = new File(path).getName();
//        response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
//        ServletOutputStream outputStream = response.getOutputStream();
//        byte[] b = new byte[1024];
//        int len;
//        //从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
//        while ((len = inputStream.read(b)) > 0) {
//            outputStream.write(b, 0, len);
//        }
//        inputStream.close();


//            //根据文件的唯一标识码获取文件
//            File uploadFile = new File(rootpath + fileName);
//
//            //读取文件的字节流
//            FileInputStream fileInputStream = new FileInputStream(uploadFile);
//            //将文件写入输入流
//            InputStream inputStream = new BufferedInputStream(fileInputStream);
//
//            byte[] buffer = new byte[inputStream.available()];
//            inputStream.read(buffer);
//            inputStream.close();
//
//
//            //attachment表示以附件方式下载 inline表示在线打开 "Content-Disposition: inline; filename=文件名.png"
//            //filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
//            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
//            response.setContentType("application/octet-stream");
//
//            //设置输出流的格式
//            ServletOutputStream os = response.getOutputStream();
//            os.write(buffer);
//
//            //关闭
//            fileInputStream.close();
//            os.flush();
//            os.close();




    }
}

