package com.zzz.pinyin.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.zzz.pinyin.service.PageService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ViewController {
    @Autowired
    private PageService pageService;

    @GetMapping("/index")
    public String getIndex() {
        // map.put("data", "index.html");
        return "index";
    }

    @GetMapping("/print")
    public void down1(HttpServletResponse response, int page_id) throws Exception {
        File path = new File("");        
        pageService.createPdf(page_id);
        String fnall = String.format("%s/pdf/%d.pdf", path.getAbsolutePath(), page_id);
        String fn = String.format("%d.pdf", page_id);
        
        response.reset();
        response.setContentType("application/pdf;charset=utf-8");
        response.setHeader(
                "Content-disposition",
                "attachment; filename=" + fn);
        try (

                BufferedInputStream bis = new BufferedInputStream(
                        new FileInputStream(fnall));
                // 输出流
                BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());) {
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = bis.read(buff)) > 0) {
                bos.write(buff, 0, len);
            }
        }
    }
}
