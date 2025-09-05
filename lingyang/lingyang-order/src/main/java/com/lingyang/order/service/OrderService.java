package com.lingyang.order.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lingyang.common.pojo.PageResult;
import com.lingyang.common.pojo.UserInfo;
import com.lingyang.common.utils.IdWorker;
import com.lingyang.item.bo.SpuBo;
import com.lingyang.item.pojo.Brand;
import com.lingyang.item.pojo.Spu;
import com.lingyang.order.interceptor.LoginInterceptor;
import com.lingyang.order.mapper.OrderDetailMapper;
import com.lingyang.order.mapper.OrderMapper;
import com.lingyang.order.mapper.OrderStatusMapper;
import com.lingyang.order.pojo.Order;
import com.lingyang.order.pojo.OrderDetail;
import com.lingyang.order.pojo.OrderStatus;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper detailMapper;

    @Autowired
    private OrderStatusMapper statusMapper;

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Transactional
    public Long createOrder(Order order) {
        // 生成orderId
        long orderId = idWorker.nextId();
        // 获取登录用户
        UserInfo user = LoginInterceptor.getLoginUser();
        // 初始化数据
        order.setBuyerNick(user.getUsername());
        order.setBuyerRate(false);
        order.setCreateTime(new Date());
        order.setOrderId(orderId);
        order.setUserId(user.getId());
        // 保存数据
        this.orderMapper.insertSelective(order);

        // 保存订单状态
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCreateTime(order.getCreateTime());
        orderStatus.setStatus(1);// 初始状态为未付款

        this.statusMapper.insertSelective(orderStatus);

        // 订单详情中添加orderId
        order.getOrderDetails().forEach(od -> od.setOrderId(orderId));
        // 保存订单详情,使用批量插入功能
        this.detailMapper.insertList(order.getOrderDetails());

        logger.debug("生成订单，订单编号：{}，用户id：{}", orderId, user.getId());

        return orderId;
    }

    public Order queryById(Long id) {
        // 查询订单
        Order order = this.orderMapper.selectByPrimaryKey(id);

        // 查询订单详情
        OrderDetail detail = new OrderDetail();
        detail.setOrderId(id);
        List<OrderDetail> details = this.detailMapper.select(detail);
        order.setOrderDetails(details);

        // 查询订单状态
        OrderStatus status = this.statusMapper.selectByPrimaryKey(order.getOrderId());
        order.setStatus(status.getStatus());
        return order;
    }

    public PageResult<Order> queryUserOrderList(Integer page, Integer rows, Integer status) {
        try {
            // 分页
            PageHelper.startPage(page, rows);
            // 获取登录用户
            UserInfo user = LoginInterceptor.getLoginUser();
            // 创建查询条件
            List<Order> orders = this.orderMapper.queryOrderList(user.getId(), status);
            orders.forEach(order->{
                order.setOrderID1(order.getOrderId().toString());
            });
            PageInfo<Order> pageInfo = new PageInfo<>(orders);
            return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        } catch (Exception e) {
            logger.error("查询订单出错", e);
            return null;
        }
    }

    @Transactional
    public Boolean updateStatus(Long id, Integer status) {
        OrderStatus record = new OrderStatus();
        record.setOrderId(id);
        record.setStatus(status);
        // 根据状态判断要修改的时间
        switch (status) {
            case 2:
                record.setPaymentTime(new Date());// 付款
                break;
            case 3:
                record.setConsignTime(new Date());// 发货
                break;
            case 4:
                record.setEndTime(new Date());// 确认收获，订单结束
                break;
            case 5:
                record.setCloseTime(new Date());// 交易失败，订单关闭
                break;
            case 6:
                record.setCommentTime(new Date());// 评价时间
                break;
            default:
                return null;
        }
        int count = this.statusMapper.updateByPrimaryKeySelective(record);
        return count == 1;
    }

    public PageResult<Order> queryOrderList(Integer page, Integer rows, String keys, Integer status) {
        //添加分页
        PageHelper.startPage(page, rows);
        //执行查询，获取orders集合
        List<Order> orders = this.orderMapper.selectByStatus(keys, status);
        orders.forEach(order->{
            order.setOrderID1(order.getOrderId().toString());
        });
        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        return  new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    public void updataOrder(Order order) {
        order.setOrderId(Long.parseLong(order.getOrderID1()));
        this.orderMapper.updateByPrimaryKeySelective(order);
    }

    public void deleteOrder(String orderId) {
        Long id = Long.parseLong(orderId);
        this.orderMapper.deleteByPrimaryKey(id);
    }
}
