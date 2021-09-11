package liu.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Controller
public class FileUpandDownController {
    //ResponseEntity自定义个响应报文 作为该方法的返回值 返回给浏览器 并呈现出来
    @RequestMapping("/testDown")
    public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
        //获取ServletContext对象 代表了整个工程
        ServletContext servletContext = session.getServletContext();
        //获取服务器中文件的真实路径 getRealPath获得服务器的部署路径（在tomcat的路径）
        String realPath = servletContext.getRealPath("/static/img/1.jpg");
        System.out.println(realPath);
        //创建输入流 文件复制前先读文件
        InputStream is = new FileInputStream(realPath);
        //创建字节数组 字节数组的长度是整个realPath对应的文件的大小
        byte[] bytes = new byte[is.available()];
        //将文件流读到字节数组中
        is.read(bytes);
        //创建HttpHeaders对象设置响应头信息 MultiValueMap是map集合
        MultiValueMap<String, String> headers = new HttpHeaders();
        //设置要下载方式以及下载文件的名字（固定写法）attachment表示以附件的方式 filename表示下载文件的默认名称
        headers.add("Content-Disposition", "attachment;filename=1.jpg");
        //设置响应状态码
        HttpStatus statusCode = HttpStatus.OK;
        //创建ResponseEntity对象
        //bytes对应的是响应体 headers是响应头 还有响应状态码
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, statusCode);
        //关闭输入流
        is.close();
        return responseEntity;
    }
    @RequestMapping("/testup")
    public String testup(MultipartFile photo,HttpSession session) throws IOException {
        System.out.println(photo.getName());
        System.out.println(photo.getOriginalFilename());
        String filename = photo.getOriginalFilename();
        ServletContext servletContext = session.getServletContext();
        //获取上传的文件的后缀名 Substring包前不包后
        String suffixName=filename.substring(filename.lastIndexOf("."));
        //将UUID作为文件名
        String uuid = UUID.randomUUID().toString();
        //将uuid和后缀名凭借后的结果作为最终文件名
        filename=uuid+suffixName;
        //获取服务器的路径 也就是服务器中photo目录的路径
        String photoPath = servletContext.getRealPath("photo");
        File file = new File(photoPath);
        //判断是否穿在photoPath
        if(!file.exists()){
            //不存在 创建一个
            file.mkdir();
        }
        String finalPath=photoPath+File.separator+filename;
        photo.transferTo(new File(finalPath));
        return "success";
    }
}
