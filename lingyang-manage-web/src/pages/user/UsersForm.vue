
<template>
  <v-form v-model="valid" ref="usersForm">
    <v-text-field v-model="user.username" label="请输入用户名称" required :rules="nameRules"/>
    <v-text-field v-model="user.phone" label="请输入用户电话" required :rules="phoneRules"/>
    <v-layout class="my-4" row>
      <v-spacer/>
      <v-btn @click="submit" color="primary">提交</v-btn>
      <v-btn @click="clear">重置</v-btn>
    </v-layout>
  </v-form>
</template>


<script>
export default {
  name: "users-form",
  props: {
    oldUsers: {
      type: Object
    },
    isEdit: {
      type: Boolean,
      default: false
    },
  },
  data() {
    return {
      valid: false, // 表单校验结果标记
      user: {
        username: '', // 用户名称
        phone: '', // 用户电话
        password:'',
        created: '',
        salt:''
      },
      nameRules: [
        v => !!v || "用户名称不能为空",
        v => /^[a-zA-Z0-9_-]{4,30}$/.test(v)  || "用户名称4~30位"
      ],
      phoneRules: [
        v => /^1[35678]\d{9}$/.test(v)  || "电话号码不能为空"
      ]
    }
  },
  methods: {
    submit() {
      // 表单校验
      if (this.$refs.usersForm.validate()) {
        let time = new Date();
        this.user.created = time;
        this.$http({
          method: this.isEdit ? 'put' : 'post',
          url: '/user/user',
          data: this.user
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
      this.$refs.usersForm.reset();
    }
  },
  watch: {
    oldUsers: {// 监控oldBrand的变化
      handler(val) {
        if (val) {
          // 注意不要直接复制，否则这边的修改会影响到父组件的数据，copy属性即可
          this.user = Object.deepCopy(val)
        } else {
          // 为空，初始化brand
          this.user =  {
            name: '', // 用户名称
            phone: '', // 用户电话
            password:'',
            created: '',
            salt:''
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
