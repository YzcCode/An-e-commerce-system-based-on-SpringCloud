<template>
  <v-form v-model="valid" ref="ordersForm">
    <v-text-field v-model="order.totalPay1" label="请输入总金额" required :rules="totalPayRules"/>
    <v-text-field v-model="order.postFee1" label="请输入邮费" required :rules="postFeeRules"/>
    <v-layout class="my-4" row>
      <v-spacer/>
      <v-btn @click="submit" color="primary">提交</v-btn>
      <v-btn @click="clear">重置</v-btn>
    </v-layout>
  </v-form>
</template>

<script>
export default {
  name: "order-form",
  props: {
    oldOrders: {
      type: Object
    },
  },
  data(){
    return{
      valid: false, // 表单校验结果标记
      order: {
        totalPay1: 0, // 总金额
        postFee1: '', // 邮费
      },
      totalPayRules: [
        v => !!v || "总金额不能为空",
        v => /^[0-9]+([.][0-9]{1,})?$/.test(v) || "总金额由数字组成"
      ],
      postFeeRules: [
        v => !!v || "邮费不能为空",
        v => /^[0-9]+([.][0-9]{1,})?$/.test(v) || "邮费由数字组成"
      ],
    }
  },
  methods:{
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
    submit() {
      // 表单校验
      if (this.$refs.ordersForm.validate()) {
        if(this.order.totalPay !== parseInt(this.order.totalPay1) * 100){
          this.order.totalPay = parseInt(this.order.totalPay1) * 100;
        }
        if(this.order.postFee !== parseInt(this.order.postFee1) * 100){
          this.order.postFee = (parseInt(this.order.postFee1) * 100).toString();
        }
        this.order.actualPay = this.order.totalPay + parseInt(this.order.postFee);
        this.$http({
          method: 'put',
          url: '/order/update',
          data: this.order
        }).then(() => {
          // 关闭窗口
          this.$emit("close");
          this.$message.success("保存成功！");
        })
          .catch(() => {
            this.$message.error("保存失败！");
          });
      }
    },
    clear() {
      // 重置表单
      this.$refs.ordersForm.reset();
    }
  },
  watch: {
    oldOrders: {// 监控oldOrders的变化
      handler(val) {
        if (val) {
          // 注意不要直接复制，否则这边的修改会影响到父组件的数据，copy属性即可
          this.order = Object.deepCopy(val);
          this.order.totalPay1 = this.formatPrice(this.order.totalPay);
          this.order.postFee1 = this.formatPrice(parseInt(this.order.postFee));
        } else {
          // 为空，初始化order
          this.order =  {
            totalPay: 0, // 总金额
            postFee: '', // 邮费
            actualPay:0,//实际金额
          }
        }
      },
      deep: true
    }
  }
}
</script>

<style scoped>

</style>
