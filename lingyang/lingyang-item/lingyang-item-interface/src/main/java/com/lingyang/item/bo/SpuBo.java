package com.lingyang.item.bo;

import com.lingyang.item.pojo.Sku;
import com.lingyang.item.pojo.Spu;
import com.lingyang.item.pojo.SpuDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author yangzicheng
 * @Date Created in 21:11 2023/3/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpuBo extends Spu {
    private String cname;

    private String bname;

    private SpuDetail spuDetail;

    private List<Sku> skus;
}
