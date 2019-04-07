package com.giga.ehospital.reservation.model.base;

import org.apache.commons.lang3.StringUtils;

public class Page {

    private String total;
    private String pageSize;
    private String pageNo;

    public String getTotal() {
        return this.total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Integer getStartRecode() {
        Integer recode = (Integer.parseInt(this.getPageNo()) - 1) * Integer.parseInt(this.getPageSize());
        return recode.intValue() < 0 ? 0 : recode.intValue();
    }

    public String getPageSize() {
        return StringUtils.isEmpty(this.pageSize) ? "10" : this.pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageNo() {
        return StringUtils.isEmpty(this.pageNo) ? "1" : this.pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }
}
