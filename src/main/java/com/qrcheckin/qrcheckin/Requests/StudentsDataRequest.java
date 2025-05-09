package com.qrcheckin.qrcheckin.Requests;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class StudentsDataRequest extends DashboardDataRequest{
    List<String> group;
}
