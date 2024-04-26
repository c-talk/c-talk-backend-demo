package me.a632079.ctalk.vo;

import lombok.Data;

/**
 * @className: PageForm
 * @description: PageForm - 公共分页
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
public class PageForm {
    private Integer pageNum;
    private Integer pageSize;
}
