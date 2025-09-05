package com.lingyang.item.mapper;

import com.lingyang.item.pojo.Spu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author yangzicheng
 * @Date Created in 21:13 2023/3/10
 */
public interface SpuMapper extends Mapper<Spu> {

    @Update("update tb_spu set valid = 0 where id = #{spuId}")
    void deleteSpu(Long spuId);

    @Update("update tb_spu set saleable = #{saleable} where id = #{spuId}")
    void isSaleable(@Param("spuId") Long spuId, @Param("saleable") int saleable);
}
