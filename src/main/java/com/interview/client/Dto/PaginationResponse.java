package com.interview.client.Dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginationResponse<T> {

    private List<T> records;

    private int pageNumber;

    private int pageSize;

    private long totalRecords;

    private int totalPages;

    private boolean first;

    private boolean last;
}
