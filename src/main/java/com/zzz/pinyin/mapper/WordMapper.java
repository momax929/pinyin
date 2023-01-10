package com.zzz.pinyin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Insert;

import com.zzz.pinyin.entity.Word;

@Mapper
public interface WordMapper {

    @Select("select * from word order by lession, question")
    public List<Word> getAll();

    @Select("select * from word where question = #{question}")
    public Word getWordByQuestion(String question);

    @Select("select * from word where word_id = #{word_id}")
    public Word getWordByWord_id(String word_id);

    @Select("select count(1) from word")
    public int getCount();

    @Select("select count(1) from word where flag = #{flag}")
    public int getCountByFlag(int flag);

    @Select("select count(1) from word where printed = #{printed}")
    public int getCountByPrinted(int printed);

    @Select("select * from word where flag = #{flag}  and printed = 0  order by lession, question LIMIT 300 ")
    public List<Word> getWordByFlagWithOrder(int flag);

    @Select("select * from word where flag = #{flag}  and printed = 0 order by RANDOM() LIMIT 300 ")
    public List<Word> getWordByFlagWithRand(int flag);

    @Insert("insert into word(word_id, question, lession, answer, flag, printed ) "
            + " values(#{word_id}, #{question}, #{lession}, #{answer}, #{flag}, #{printed})")
    public void insert(Word word);

    @Update("update word set flag = #{flag}, printed = #{printed} where word_id = #{word_id} ")
    public void updateFlag(String word_id, int flag, int printed);

    @Update("update word set printed = #{printed} where word_id = #{word_id} ")
    public void updatePrinted(String word_id, int printed);    

    @Update("update word set flag = 0, printed = 0 ")
    public void updateFlagTo0();
}
