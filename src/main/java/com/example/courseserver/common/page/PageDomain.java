package com.example.courseserver.common.page;

import lombok.Data;

@Data
public class PageDomain {
    private Integer pageNum;
    private Integer pageSize;
    private String orderByColumn;
    private String asAsc = "asc";
    private Boolean reasonable = true;
}
