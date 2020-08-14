package com.example.springboot.Util;

import java.util.List;
import lombok.Getter;
import org.springframework.util.Assert;

/**
 * 集合分页util, 当然也支持计算偏移,可用于mybatis
 * @author xuzhen97
 */
public class PageUtil<T> {

    @Getter
    private int currPage = 1;
    @Getter
    private int pageSize = 5;
    @Getter
    private int totalPage = 0;
    @Getter
    private int totalSize;
    @Getter
    private List<T> list = null;

    public PageUtil(int currPage, int pageSize, List<T> list) {
        Assert.isTrue(currPage > 0, "currPage必须大于0!");
        Assert.isTrue(pageSize > 0 , "pageSize必须大于0!");
        Assert.notNull(list, "list不能为null!");
        this.currPage = currPage;
        this.pageSize = pageSize;
        this.list = list;
        this.totalSize = this.list.size();
        this.totalPage = totalSize / pageSize + (totalSize % pageSize > 0 ? 1 : 0);
    }

    public PageUtil(int currPage, int pageSize, int totalSize){
        Assert.isTrue(currPage > 0, "currPage必须大于0!");
        Assert.isTrue(pageSize > 0 , "pageSize必须大于0!");
        Assert.isTrue(totalSize >= 0 , "totalSize必须大于等于0!");
        this.currPage = currPage;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
        if(totalSize > 0) {
            this.totalPage = totalSize / pageSize + (totalSize % pageSize > 0 ? 1 : 0);
        }else{
            this.totalPage = 1;
        }
    }

    /**
     * 获取当前页的的开始坐标索引
     * @return
     */
    public int getOffset(){
        return ((this.currPage - 1) * this.pageSize);
    }

    /**
     * 获取这一页的数量
     * @return
     */
    public int getLimit(){
        int count=Math.min(this.currPage * this.pageSize, totalSize) - getOffset();
        return count>0?count:0;
    }

    /**
     * 获取当前页的数据
     * @return
     */
    public List<T> nextPage(){
        if(currPage <= totalPage && list != null) {
            int listNum = this.currPage * this.pageSize;

            if (listNum > list.size()) {
                listNum = list.size();
            }

            List<T> result = list.subList((this.currPage - 1) * this.pageSize, listNum);

            this.currPage++;

            return result;
        }else{
            return null;
        }
    }

    /**
     * 设置当前页
     * @param currPage
     */
    public void setCurrPage(int currPage){
        Assert.isTrue(currPage > 0, "currPage必须大于0!");
        this.currPage = currPage;
    }

}
