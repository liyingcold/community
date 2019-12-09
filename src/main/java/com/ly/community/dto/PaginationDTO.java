package com.ly.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
//    向前按钮
    private boolean showPrevious;
//    第一页按钮
    private boolean showFirstPage;
//    下一页
    private boolean showNext;
//    最后一页
    private boolean showEndPage;
//    当前页
    private  Integer page;

    private List<Integer> pages=new ArrayList<>();

    public void setPagination(Integer totalCount, Integer page, Integer size) {
        this.page=page;
//总页数
        Integer totalPage =0;
        if (totalCount % size ==0){
            totalPage = totalCount /size;
        }else {
            totalPage = totalCount /size +1;
        }

        pages.add(page);

        for (int i=1;i<=3;i++){
            if (page-i>0){
                pages.add(0,page-i);
            }

            if (page+i<=totalPage){
                pages.add(page +i);
            }
        }


//是否展示上一页(第一页时不展示，否则全展示)
        if (page == 1){
            showPrevious =false;
        }else {
            showPrevious =true;
        }
//        是否展示下一页（最后一页时不展示，否则全展示）
        if (page == totalPage){
            showNext =false;
        }else {
            showNext =true;
        }
//        是否展示第一页
        if (pages.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage =true;
        }
//        是否展示最后一页
        if (pages.contains(totalPage)){
            showEndPage =false;
        }else {
            showEndPage =true;
        }

    }
}
