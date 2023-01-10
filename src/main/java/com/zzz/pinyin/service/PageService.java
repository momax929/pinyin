package com.zzz.pinyin.service;

import java.util.List;

import com.zzz.pinyin.entity.Page;

public interface PageService {
    int newPage(String sort, int error_num);

    void createPdf(int page_id);
    
    List<Page> getAll();

    List<Page> getAllByPage(int start, int count);

    Page getPageByPage_id(int page_id);

    void delPageByPage_id(int page_id);
    
    List<String> getPageQuestionList(int page_id);

    void setPageWordFlag(int page_id, String word_id, int flag);

    void initWordFlag();

    double getAvgPoint();
}
