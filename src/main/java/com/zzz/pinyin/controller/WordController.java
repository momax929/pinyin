package com.zzz.pinyin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zzz.pinyin.entity.Word;
import com.zzz.pinyin.entity.WordCount;
import com.zzz.pinyin.service.WordService;

@RestController
@RequestMapping("/word")
public class WordController {
    @Autowired
    private WordService wordService;

    @GetMapping("/all")
    public List<Word> getAll(){
        return wordService.getAll();
    }

    @GetMapping("/initDict")
    public void initDict(){
        wordService.initDict();
    }

    @GetMapping("/count")
    public WordCount getCount(){
        return wordService.getCount();
    }

    @GetMapping("/countByFlag")
    public int getCountByFlag(int flag){
        return wordService.getCountByFlag(flag);
    }
}
