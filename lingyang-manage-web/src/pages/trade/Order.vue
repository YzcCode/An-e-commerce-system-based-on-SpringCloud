<template>
  <v-card>
    <v-toolbar class="elevation-0">
      <v-flex xs3 style="margin-left: 340px;display: inline">
        <v-btn-toggle mandatory v-model="filter.status">
          <v-btn flat>
            全部
          </v-btn>
          <v-btn flat :value="1">
            未付款
          </v-btn>
          <v-btn flat :value="2">
            已付款未发货
          </v-btn>
          <v-btn flat :value="3">
            已发货未确认
          </v-btn>
          <v-btn flat :value="4">
            交易关闭
          </v-btn>
          <v-btn flat :value="5">
            已评价
          </v-btn>
        </v-btn-toggle>
      </v-flex>
      <v-flex xs3 style="margin-left: 230px">
        <v-text-field
          append-icon="search"
          label="搜索"
          single-line
          hide-details
          v-model="filter.search"
        />
      </v-flex>
    </v-toolbar>
    <v-divider/>
    <v-data-table
      :headers="headers"
      :items="OrdersList"
      :pagination.sync="pagination"
      :total-items="totalOrders"
      :loading="loading"
      class="elevation-1"
    >
      <template slot="items" slot-scope="props">
        <td class="text-xs-center">{{ props.item.orderID1 }}</td>
        <td class="text-xs-center">{{ props.item.buyerNick }}</td>
        <td class="text-xs-center">{{props.item.receiverMobile}}</td>
        <td class="text-xs-center">￥{{ formatPrice(props.item.totalPay)}}</td>
        <td class="text-xs-center">￥{{ formatPrice(parseInt(props.item.postFee))}}</td>
        <td class="text-xs-center">￥{{ formatPrice(props.item.actualPay)}}</td>
        <td class="text-xs-center">{{ props.item.payType}}</td>
        <td class="text-xs-center">{{ props.item.invType}}</td>
        <td class="text-xs-center">{{ props.item.souType}}</td>
        <td class="text-xs-center">{{ props.item.createTime}}</td>
        <td class="text-xs-center" style="font-size: 10px">{{ props.item.address}}</td>
        <td class="justify-center layout px-0">
          <v-btn icon @click="editOrders(props.item)">
            <i class="el-icon-edit"/>
          </v-btn>
          <v-btn icon @click="deleteOrders(props.item)">
            <i class="el-icon-delete"/>
          </v-btn>
        </td>
      </template>
    </v-data-table>
    <!--弹出的对话框-->
    <v-dialog max-width="800" v-model="show" persistent scrollable>
      <v-card>
        <!--对话框的标题-->
        <v-toolbar dense dark color="primary">
          <v-toolbar-title>修改订单</v-toolbar-title>
          <v-spacer/>
          <!--关闭窗口的按钮-->
          <v-btn icon @click="closeWindow">
            <v-icon>close</v-icon>
          </v-btn>
        </v-toolbar>
        <!--对话框的内容，表单-->
        <v-card-text class="px-3" style="height: 600px">
          <order-form :oldOrders="oldOrders"  @close="closeWindow" ref="ordersForm"/>
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-card>
</template>
<script>
import OrderForm from "./OrderForm";
import moment from 'moment';

export default {
  name: "Order",
  data() {
    return {
      filter: {
        search: '', // 搜索过滤字段
        status:0,
      },
      totalOrders: 0, // 总用户数
      OrdersList: [], // 当前页订单数据
      loading: true, // 是否在加载中
      pagination: {}, // 分页信息
      headers: [
        {text: '订单号', align: 'center', sortable: false, value: 'orderID1'},
        {text: '用户名', align: 'center', sortable: false, value: 'buyerNick'},
        {text: '电话号码', align: 'center', sortable: false, value: 'receiverMobile'},
        {text: '总金额', align: 'center', sortable: false, value: 'totalPay'},
        {text: '邮费', align: 'center', sortable: false, value: 'postFee'},
        {text: '实付金额', align: 'center', sortable: false, value: 'actualPay'},
        {text: '支付类型', align: 'center', sortable: false, value: 'paymentType'},
        {text: '发票类型', align: 'center', sortable: false, value: 'invoiceType'},
        {text: '订单来源', align: 'center', sortable: false, value: 'sourceType'},
        {text: '创建时间', align: 'center', sortable: false, value: 'createTime'},
        {text: '收货地址', align: 'center', sortable: false, value: 'address'},
        {text: '操作', align: 'center', sortable: false}
      ],
      show: false,// 控制对话框的显示
      oldOrders: {}, // 即将被编辑的商品信息
    }
  },
  mounted() { // 渲染后执行
    // 查询数据
    this.getDataFromServer();
  },
  watch: {
    pagination: { // 监视pagination属性的变化
      deep: true, // deep为true，会监视pagination的属性及属性中的对象属性变化
      handler() {
        // 变化后的回调函数，这里我们再次调用getDataFromServer即可
        this.getDataFromServer();
      }
    },
    filter: {// 监视搜索字段
      handler() {
        this.getDataFromServer();
      },
      deep: true
    }
  },
  methods: {
    getDataFromServer() { // 从服务的加载数的方法。
      // 发起请求
      this.$http.get("/order/listPage?", {
        params: {
          keys: this.filter.search, // 搜索条件
          page: this.pagination.page,// 当前页
          rows: this.pagination.rowsPerPage,// 每页大小
          status: this.filter.status,
        },
      }).then(resp => { // 这里使用箭头函数
        this.OrdersList = resp.data.items;
        this.OrdersList.forEach(order=>{
          order.createTime = moment(order.createTime).format("YYYY-MM-DD");
          order.address = order.receiverState+"(省)"+ order.receiverCity+"(市)"+order.receiverDistrict+"(区/县)"+order.receiverAddress;
          order.payType = this.formatPaymentType(order.paymentType);
          order.invType = this.formatInvoiceType(order.invoiceType);
          order.souType = this.formatSourceType(order.sourceType);
        })
        this.totalOrders = resp.data.total;
        // 完成赋值后，把加载状态赋值为false
        this.loading = false;
      })
    },
    editOrders(oldOrders) {
      // 控制弹窗可见：
      this.show = true;
      // 获取要编辑的goods
      this.oldOrders = oldOrders;
    },
    deleteOrders(oldOrders){
      this.$message.confirm("确认要删除该订单吗？")
        .then(() => {
          this.$http.delete("/order/delete/" + oldOrders.orderID1)
            .then(() => {
              this.$message.success("删除成功");
              this.getDataFromServer();
            })
            .catch(() => {
              this.$message.error("删除失败");
            })
        })
    },
    closeWindow() {
      console.log(1)
      // 重新加载数据
      this.getDataFromServer();
      //关闭窗口
      this.show = false;
    },
    formatPrice(val) {
      if(typeof val === 'string'){
        if(isNaN(val)){
          return null;
        }
        // 价格转为整数
        const index = val.lastIndexOf(".");
        let p = "";
        if(index < 0){
          // 无小数
          p = val + "00";
        }else if(index === p.length - 2){
          // 1位小数
          p = val.replace("\.","") + "0";
        }else{
          // 2位小数
          p = val.replace("\.","")
        }
        return parseInt(p);
      }else if(typeof val === 'number'){
        if(val == null){
          return null;
        }
        const s = val + '';
        if(s.length === 0){
          return "0.00";
        }
        if(s.length === 1){
          return "0.0" + val;
        }
        if(s.length === 2){
          return "0." + val;
        }
        const i = s.indexOf(".");
        if(i < 0){
          return s.substring(0, s.length - 2) + "." + s.substring(s.length-2)
        }
        const num = s.substring(0,i) + s.substring(i+1);
        if(i === 1){
          // 1位整数
          return "0.0" + num;
        }
        if(i === 2){
          return "0." + num;
        }
        if( i > 2){
          return num.substring(0,i-2) + "." + num.substring(i-2)
        }
      }
    },
    formatPaymentType(val){
      if(val == 1){
        return "在线支付";
      }else{
        return "货到付款";
      }
    },
    formatInvoiceType(val){
      if(val == 0){
        return "无发票";
      }else if(val == 1){
        return "普通发票";
      }else if(val == 2){
        return "电子发票";
      }else if(val == 3){
        return "增值税发票";
      }
    },
    formatSourceType(val){
      if(val == 1){
        return "app端";
      }else if(val == 2){
        return "pc端";
      }else if(val == 3){
        return "M端";
      }else if(val == 4){
        return "微信端";
      }else if(val == 5){
        return "手机qq端";
      }
    },
  },
  components: {
    OrderForm,
  }
}
</script>

<style scoped>

</style>
