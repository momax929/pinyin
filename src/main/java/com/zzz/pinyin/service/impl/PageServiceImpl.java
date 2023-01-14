package com.zzz.pinyin.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.zzz.pinyin.entity.Jour;
import com.zzz.pinyin.entity.Page;
import com.zzz.pinyin.entity.Word;
import com.zzz.pinyin.mapper.JourMapper;
import com.zzz.pinyin.mapper.PageMapper;
import com.zzz.pinyin.mapper.WordMapper;
import com.zzz.pinyin.service.PageService;

@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private PageMapper pageMapper;

    @Autowired
    private WordMapper wordMapper;

    @Autowired
    private JourMapper jourMapper;

    @Override
    public int newPage(String sort, int error_num) {
        List<Word> lst1;
        List<Word> lst2;
        if (sort.equals("随机")) {
            lst1 = wordMapper.getWordByFlagWithRand(0);
            lst2 = wordMapper.getWordByFlagWithRand(2);
            Collections.reverse(lst1);
            Collections.reverse(lst2);
        } else {
            lst1 = wordMapper.getWordByFlagWithOrder(0);
            lst2 = wordMapper.getWordByFlagWithOrder(2);
        }

        List<Jour> lst = new ArrayList<>();
        int len_max = 74 + 4;
        int len_max_no = 17;
        int page_id = 0;

        if (pageMapper.getCount() == 0)
            page_id = 1;
        else
            page_id = pageMapper.getMaxPage_id() + 1;

        int error_no = error_num;
        int order = 1;

        for (int i = 0; i < len_max_no; i++) {
            int line_len = 0;
            if (lst2.size() > 0 && error_no > 0) {
                for (int j = lst2.size() - 1; j >= 0; j--) {
                    if (error_no <= 0)
                        break;
                    Word w = lst2.get(j);
                    if (line_len + w.getQuestion().length() + 4 <= len_max) {
                        Jour jour = new Jour();
                        jour.setFlag(0);
                        jour.setLine_no(i);
                        jour.setOrder_no(order++);
                        jour.setPage_id(page_id);
                        jour.setWord_id(w.getWord_id());
                        lst.add(jour);
                        lst2.remove(w);
                        error_no--;
                        line_len += w.getQuestion().length() + 4;
                    }
                }
            }

            if (lst1.size() > 0) {
                for (int j = lst1.size() - 1; j >= 0; j--) {
                    Word w = lst1.get(j);
                    if (line_len + w.getQuestion().length() + 4 <= len_max) {
                        Jour jour = new Jour();
                        jour.setFlag(0);
                        jour.setLine_no(i);
                        jour.setOrder_no(order++);
                        jour.setPage_id(page_id);
                        jour.setWord_id(w.getWord_id());
                        lst.add(jour);
                        lst1.remove(w);
                        line_len += w.getQuestion().length() + 4;
                    }
                }
            }
        }

        for (Jour j : lst) {
            jourMapper.insert(j);
            wordMapper.updatePrinted(j.getWord_id(), 1);
        }

        Page page = new Page();
        page.setAnswer_error(0);
        page.setAnswer_right(0);
        page.setCount(lst.size());
        page.setCount_error(error_num - error_no);
        page.setCount_new(page.getCount() - page.getCount_error());
        page.setPrint_date(new Date());
        page.setAnswer_date(new Date());
        page.setPage_id(String.valueOf(page_id));
        pageMapper.insert(page);
        createPdf(page_id);
        return page_id;
    }

    @Override
    public List<Page> getAll() {
        return pageMapper.getAll();
    }

    @Override
    public Page getPageByPage_id(int page_id) {
        return pageMapper.getPageByPage_id(page_id);
    }

    @Override
    public List<Page> getAllByPage(int start, int count) {
        return pageMapper.getAllByPage(start, count);
    }

    @Override
    public void delPageByPage_id(int page_id) {
        jourMapper.insertToHis(page_id);
        jourMapper.deleteByPage_id(page_id);
        pageMapper.insertToHis(page_id);
        pageMapper.delByPage_id(page_id);
        initWordFlag();
    }

    public void createPdf(int page_id) {
        List<String> lst = getPageQuestionList(page_id);
        Page p = pageMapper.getPageByPage_id(page_id);
        String title = String.format("序号%s, 总数%d, 其中历史错题%d", p.getPage_id(), p.getCount(), p.getCount_error());
        String fn = p.getPage_id() + ".pdf";

        Document d = new Document(PageSize.A4);
        d.setPageCount(1);
        File path = new File("");// 设定为当前文件夹
        File file = new File(path.getAbsolutePath() + "/pdf/" + fn);
        String fontfn = path.getAbsolutePath() + "/pdf/simsun.ttc,0";
        try {
            file.createNewFile();
            PdfWriter.getInstance(d, new FileOutputStream(file));
            d.open();
            BaseFont bFont = BaseFont.createFont(fontfn, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titlefont = new Font(bFont, 8, Font.BOLD);
            Font textfont = new Font(bFont, 14, Font.NORMAL);
            d.add(new Paragraph(title, titlefont));
            for (String s : lst)
                if (s.equals(""))
                    d.add(new Paragraph("       ", textfont));
                else
                    d.add(new Paragraph(s, textfont));
            d.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getPageQuestionList(int page_id) {
        List<String> lst = new ArrayList<>();
        List<Jour> lstj = jourMapper.getJourByPage_id(page_id);
        int lines = 0;
        for (Jour jour : lstj) {
            if (jour.getLine_no() > lines)
                lines = jour.getLine_no();
        }
        for (int i = 0; i <= lines * 2; i++)
            lst.add("");

        for (Jour j : lstj) {
            Word w = wordMapper.getWordByWord_id(j.getWord_id());
            int line_no = j.getLine_no() * 2;
            lst.set(line_no, (lst.get(line_no) + "    " + w.getQuestion()).trim());
        }
        return lst;
    }

    public void setPWFlag(int page_id, String word_id, int flag) {
        int printed = 1;
        if (flag == 2)
            printed = 0;
        wordMapper.updateFlag(word_id, flag, printed);
        jourMapper.updateFlag(page_id, word_id, flag);

        int error = jourMapper.getCountByPage_idFlag(page_id, 2);
        int right = jourMapper.getCountByPage_idFlag(page_id, 1);
        // log.info("{} {} {} {}", page_id, new Date(), right, error);
        pageMapper.updateFlag(page_id, new Date(), right, error);
    }

    @Override
    public void setPageWordFlag(int page_id, String word_id, int flag) {
        if (!word_id.equals(""))
            setPWFlag(page_id, word_id, flag);
        else
            for (Jour j : jourMapper.getJourByPage_id(page_id)) {
                if (j.getFlag() == 0) {
                    setPWFlag(page_id, j.getWord_id(), 1);
                }
            }
    }

    @Override
    public void initWordFlag() {
        wordMapper.updateFlagTo0();
        List<Jour> lj = jourMapper.getAll();
        for (Jour jour : lj) {
            if (jour.getFlag() != 0) {
                int printed = 1;
                if (jour.getFlag() == 2)
                    printed = 0;
                wordMapper.updateFlag(jour.getWord_id(), jour.getFlag(), printed);
            } else {
                wordMapper.updatePrinted(jour.getWord_id(), 1);
            }
        }
    }

    @Override
    public double getAvgPoint() {
        List<Page> pl = pageMapper.getAll();
        double total = 0;
        double point = 0;
        for (Page p : pl) {
            if (p.getCount() != 0 && p.getCount() == p.getAnswer_error() + p.getAnswer_right()) {
                total = total + 1;
                point = point + p.getAnswer_right() / (p.getCount() * 1.0) * 100;
            }
        }
        if (total > 0)
            return point / total;
        return 100;
    }
}
