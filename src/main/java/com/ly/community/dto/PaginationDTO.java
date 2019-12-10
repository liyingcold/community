package com.ly.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 包裹样式和前端需要的数据
 */
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
    private Integer totalPage;

    /**
     *
     * @param totalPage  总页数
     * @param page       当前页数

     */
    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage = totalPage;
//        为page配值
        this.page=page;
        pages.add(page);
        for (int i=1;i<=3;i++){
//            向前展示三个页面
            if (page-i>0){
                pages.add(0,page-i);
            }
//            向后展示三个页面
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
