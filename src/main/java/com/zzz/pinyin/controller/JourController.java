package com.zzz.pinyin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zzz.pinyin.service.JourService;
import com.zzz.pinyin.entity.Jour;
import com.zzz.pinyin.entity.PageWord;

@RestController
@RequestMapping("/jour")
public class JourController {
    @Autowired
    private JourService jourService;

    @GetMapping("/getJourByPage_id")
    public List<Jour> getJourByPage_id(String page_id) {
        if (page_id == null)
            page_id = "0";
        int pid = Integer.valueOf(page_id);
        return jourService.getJourByPage_id(pid);
    }

    @GetMapping("/getPageWord")
    public List<PageWord> getPageWord(String page_id) {
        if (page_id == null)
            page_id = "0";
        int pid = Integer.valueOf(page_id);
        return jourService.getJourWord(pid);
    }

}
