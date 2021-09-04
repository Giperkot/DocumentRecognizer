package ru.digitalsoft.document.dto.common;

public class TableResultDto extends ru.digitalsoft.document.dto.ResultDto {

    private Object result;

    private long count;

    private long page;

    private long pageCount;

    public TableResultDto(boolean success, Object result, long count, long page) {
        super(success);

        this.result = result;
        this.count = count;
        this.page = page;
        this.pageCount = 100;
    }

    public TableResultDto(boolean success, Object result, long count, long page, long pageCount) {
        super(success);

        this.result = result;
        this.count = count;
        this.page = page;
        this.pageCount = pageCount;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }
}
