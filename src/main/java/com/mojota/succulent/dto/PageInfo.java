package com.mojota.succulent.dto;

/**
 * @author jamie
 * @date 18-9-12
 */
public class PageInfo {
    int pageSize;
    int pageNumber;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
