package com.zzz.pinyin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzz.pinyin.entity.Jour;
import com.zzz.pinyin.entity.PageWord;
import com.zzz.pinyin.mapper.JourMapper;
import com.zzz.pinyin.service.JourService;

@Service
public class JourServiceImpl implements JourService {
    @Autowired
    private JourMapper jourMapper;

    @Override
    public List<Jour> getJourByPage_id(int page_id) {
        return jourMapper.getJourByPage_id(page_id);
    }

    @Override
    public List<PageWord> getJourWord(int page_id) {
        return jourMapper.getJourWord(page_id);
    }
    
}
