package com.zzz.pinyin.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zzz.pinyin.entity.Page;
import com.zzz.pinyin.service.PageService;

@RestController
@RequestMapping("/page")
public class PageController {
    @Autowired
    private PageService pageService;

    @GetMapping("/newPage")
    public int newPage(String sort, int error_num){
        return pageService.newPage(sort, error_num);
    }

    @GetMapping("/getAvgPoint")
    public double getAvgPoint(){
        return pageService.getAvgPoint();
    }

    @GetMapping("/getAllByPage")
    public List<Page> getAllByPage(int start, int count){
        return pageService.getAllByPage(start, count);
    }

    @GetMapping("/getPageQuestionList")
    public List<String> getPageQueestionList(int page_id){
        return pageService.getPageQuestionList(page_id);
    }    

    @PostMapping("/setWordFlag")
    public void setWordFlag(int page_id, String word_id, int flag){
        pageService.setPageWordFlag(page_id, word_id, flag);
    }
    
    @PostMapping("delByPage_id")
    public void delByPage_id(int page_id){
        pageService.delPageByPage_id(page_id);
        pageService.initWordFlag();
    }

    @PostMapping("initWordFlag")
    public void initWordFlag(){
        pageService.initWordFlag();
    }

}
