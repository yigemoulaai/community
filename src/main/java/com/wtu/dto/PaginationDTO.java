package com.wtu.dto;

import com.wtu.model.Question;

import java.util.ArrayList;
import java.util.List;

public class PaginationDTO {
    private List<QuestionDTO> questionDTOs;
    private  boolean showPrevious;
    private  boolean showFirstPage;
    private  boolean showNext;
    private  boolean showEndPage;
    private  Integer page;
    private  List<Integer> pages=new ArrayList<>();
    private  Integer totalPage;

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<QuestionDTO> getQuestionDTOs() {
        return questionDTOs;
    }

    public void setQuestionDTOs(List<QuestionDTO> questionDTOs) {
        this.questionDTOs = questionDTOs;
    }

    public boolean isShowPrevious() {
        return showPrevious;
    }

    public void setShowPrevious(boolean showPrevious) {
        this.showPrevious = showPrevious;
    }

    public boolean isShowFirstPage() {
        return showFirstPage;
    }

    public void setShowFirstPage(boolean showFirstPage) {
        this.showFirstPage = showFirstPage;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    public boolean isShowEndPage() {
        return showEndPage;
    }

    public void setShowEndPage(boolean showEndPage) {
        this.showEndPage = showEndPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    public void setPagination(Integer totalcount, Integer page, Integer size,Integer totalPage) {


        //计算总页数
        this.totalPage=totalPage;

        this.page=page;
        //计算分页页码
        this.pages.add(page);
        for (int i=1;i<=3;i++)
        {
            if(page-i >=1)
            {
                pages.add(0,page-i);
            }
            if(page+i<=totalPage)
            {
                pages.add(page+i);
            }
        }


        if (page == 1)
        {
            showPrevious=false;
        }
        else {
            showPrevious=true;
        }
        if(page==totalPage)
        {
            showNext=false;
        }
        else {
            showNext=true;
        }
        //是否展示第一页
        if(pages.contains(1))
        {
        showFirstPage=false;
        }
        else {showFirstPage=true;}
        //是否展示最后一页
        if(pages.contains(totalPage))
        {
            showEndPage=false;
        }
        else
        {
            showEndPage=true;
        }


    }
}
