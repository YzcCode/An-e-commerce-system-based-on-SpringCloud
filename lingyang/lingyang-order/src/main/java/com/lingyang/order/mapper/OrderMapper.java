package com.lingyang.order.mapper;

import com.lingyang.order.pojo.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrderMapper extends Mapper<Order> {

    List<Order> queryOrderList(
            @Param("userId") Long userId,
            @Param("status") Integer status);

    List<Order> selectByStatus(@Param("keys") String keys,@Param("status") Integer status);
    @Update("update tb_order set total_pay = #{totalPay}, actual_pay = #{actualPay}, post_fee = #{postFee} where order_id = #{id}")
    void update(@Param("id") Long id, @Param("totalPay") Long totalPay,@Param("actualPay") Long actualPay, @Param("postFee") String postFee);
}
