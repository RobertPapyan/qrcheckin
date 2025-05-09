package com.qrcheckin.qrcheckin.Requests;

import lombok.Data;

import java.util.List;

@Data
public class DashboardDataRequest {
    Integer page = 0;
    String sort;
    String orderBy = "asc";
    String search;
}
