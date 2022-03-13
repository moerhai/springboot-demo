package com.mohai.one.springbootfileupload.web;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
public class FileUploadController {

    @PostMapping(value = "/upload")
    public String upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("description") String description) throws Exception {
        if (file == null || file.isEmpty()) {
            return "文件为空";
        }
        //获取文件名
        String fileName = file.getOriginalFilename();
        System.out.println("文件名称："+fileName);//打印文件上传名称
        System.out.println("文件描述："+description);//打印文件上传名称
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String subPath = sdf.format(new Date());
        String basePath = subPath + "/" + fileName;
        System.out.println("保存文件路径："+basePath);
        File dest = new File(basePath);
        //检测是否存在目录
        if (!dest.getParentFile().exists()) {
            //新建文件夹
            dest.getParentFile().mkdirs();
        }
        file.transferTo(dest);//文件保存
        return "SUCCESS";
    }


    @PostMapping("/uploads")
    public String uploads(MultipartFile[] uploadFiles, HttpServletRequest request) {
        // List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        String realPath = request.getSession().getServletContext().getRealPath("/uploadFile/");
        System.out.println(realPath);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String subPath = sdf.format(new Date());
        for (MultipartFile uploadFile : uploadFiles) {
            File folder = new File(realPath + subPath);
            if (!folder.isDirectory()) {
                folder.mkdirs();
            }
            String oldName = uploadFile.getOriginalFilename();
            try {
                uploadFile.transferTo(new File(folder, oldName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "SUCCESS";
    }

    private void handleFileUpload(List<MultipartFile> files,String realPath) {
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    String fileName = file.getOriginalFilename();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    String subPath = sdf.format(new Date());
                    String basePath = subPath + "/" + fileName;
                    stream = new BufferedOutputStream(new FileOutputStream(
                            new File(realPath + basePath)));//设置文件路径及名字
                    stream.write(bytes);// 写入
                    stream.flush();
                } catch (Exception e) {
                    try {
                        if(stream != null){
                            stream.close();
                        }
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }
            }
        }
    }

}
