package com.wei.fileservice.controller;


import com.wei.fileservice.service.FileService;
import com.wei.fileservice.util.BandwidthLimiter;
import com.wei.fileservice.util.DownloadLimiter;
import entity.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class FileController {

    @Resource
    FileService fileService;

    String beforeString = "--";
    @Value("${config.maxRate}")
    Integer maxRate = 200;
    @Value("${config.bufferSize}")
    Integer bufferSize = 8192;
    @Value("${config.diffTime}")
    Long diffTime;
    @Value("${config.password}")
    String password;
    @Value("${config.maxUploadSize}")
    Long maxUploadSize;
    @Value("${config.localhost}")
    String localhost;
    Map<String, Date> cache = new HashMap<>();

    @GetMapping("/download")
    public void download(@RequestParam("code")String code, HttpServletResponse response) throws IOException {
        code = code.replaceAll(" +", "+");
        byte[] decode = Base64Utils.decodeFromString(code);
        String path = new String(decode);
        if (!fileService.isFileExist(path)) {
            return;
        }
        File file = fileService.getFile(path);
        String fileName = path.split(beforeString)[1];
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "attachment;fileName=" +  URLEncoder.encode(fileName, "UTF-8"));
        FileInputStream fileInputStream = new FileInputStream(file);
        ServletOutputStream outputStream = response.getOutputStream();
        DownloadLimiter limiter = new DownloadLimiter(fileInputStream, new BandwidthLimiter(maxRate));
        byte[] cache = new byte[bufferSize];
        while (limiter.read(cache) != -1){
            outputStream.write(cache);
        }
        outputStream.flush();
        outputStream.close();
        fileInputStream.close();
    }

    @GetMapping("/list")
    public Response list(@RequestParam("pw")String pw){
        if (!StringUtils.hasText(pw)) return Response.fail(Response.ResponseState.BAD_REQUEST, "pw??????");
        if (!pw.equals(password)) return Response.fail(Response.ResponseState.BAD_REQUEST, "pw?????????");
        return Response.ok(fileService.list());
    }

    @GetMapping("/deleteByPath")
    public Response list(@RequestParam("ip")String ip,
                         @RequestParam("path") String path
                        ,@RequestParam("pw")String pw){
        if (!StringUtils.hasText(pw)) return Response.fail(Response.ResponseState.BAD_REQUEST, "pw??????");
        if (!pw.equals(password)) return Response.fail(Response.ResponseState.BAD_REQUEST, "pw?????????");
        boolean b = fileService.deleteFileByPath(ip + File.separator + path);
        if (b)return Response.ok("????????????");
        else return Response.fail("????????????");
    }

    @PostMapping("/upload")
    public Response upload(@RequestParam(value = "file") MultipartFile file,
                           @RequestParam(value = "pw", required = false)String pw,
                           HttpServletRequest request) {
        if (file == null || file.isEmpty()){
            return Response.fail(Response.ResponseState.BAD_REQUEST, "????????????");
        }
        String ip = request.getRemoteAddr();
        if (!StringUtils.hasText(pw) || (StringUtils.hasText(pw) && !pw.equals(password))){
            //????????????
            if (file.getSize() > maxUploadSize) {
                return Response.fail(Response.ResponseState.BAD_REQUEST, "????????????????????????");
            }
            //????????????????????????????????????
//            fileService.deleteFileByIp(ip);
            //???????????????????????????
            if (cache.containsKey(ip)){
                Date date = cache.get(ip);
                if (System.currentTimeMillis() - date.getTime() < diffTime){
                    return Response.fail(Response.ResponseState.BAD_REQUEST, "????????????????????????");
                }else{
                    cache.put(ip, new Date());
                }
            }else{
                cache.put(ip, new Date());
            }
        }

        String fileName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String path = ip + File.separator +  uuid + beforeString + fileName;
        String s = fileService.saveFile(file, path);
        if (s != null) {
            String encode = Base64Utils.encodeToString(path.getBytes(Charset.defaultCharset()));
            return Response.ok(localhost + "download?code=" + encode, null);
        }
        return Response.fail("????????????");
    }




}
