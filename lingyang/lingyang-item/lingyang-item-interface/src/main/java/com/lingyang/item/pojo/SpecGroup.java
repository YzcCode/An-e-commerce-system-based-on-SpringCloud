package com.lingyang.item.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @Author yangzicheng
 * @Date Created in 21:23 2023/3/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_spec_group")
public class SpecGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cid;                   //分类id

    private String name;                //名称

    @Transient
    private List<SpecParam> params;
}
