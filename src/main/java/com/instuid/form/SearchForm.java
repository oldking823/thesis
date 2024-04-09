package com.instuid.form;

import lombok.Data;

@Data
public class SearchForm {
    private String key;
    private String value;
    private String dep;
    private Integer page;
    private Integer size;
}
