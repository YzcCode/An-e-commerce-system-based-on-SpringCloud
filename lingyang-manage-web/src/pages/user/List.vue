<template>
  <v-card>
    <v-toolbar class="elevation-0">
      <v-btn color="primary" @click="addUser">新增用户</v-btn>
      <v-spacer/>
      <v-flex xs3>
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
      :items="usersList"
      :pagination.sync="pagination"
      :total-items="totalUsers"
      :loading="loading"
      class="elevation-1"
    >
      <template slot="items" slot-scope="props">
        <td class="text-xs-center">{{ props.item.id }}</td>
        <td class="text-xs-center">{{ props.item.username }}</td>
        <td class="text-xs-center">{{props.item.phone}}</td>
        <td class="text-xs-center">{{ props.item.created}}</td>
        <td class="justify-center layout px-0">
          <v-btn icon @click="editUsers(props.item)">
            <i class="el-icon-edit"/>
          </v-btn>
        </td>
      </template>
    </v-data-table>
    <!--弹出的对话框-->
    <v-dialog max-width="800" v-model="show" persistent scrollable>
      <v-card>
        <!--对话框的标题-->
        <v-toolbar dense dark color="primary">
          <v-toolbar-title>{{isEdit ? '修改' : '新增'}}用户</v-toolbar-title>
          <v-spacer/>
          <!--关闭窗口的按钮-->
          <v-btn icon @click="closeWindow">
            <v-icon>close</v-icon>
          </v-btn>
        </v-toolbar>
        <!--对话框的内容，表单-->
        <v-card-text class="px-3" style="height: 600px">
          <users-form :oldUsers="oldUsers"  @close="closeWindow" :is-edit="isEdit" ref="usersForm"/>
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-card>
</template>

<script>
// 导入自定义的表单组件
import UsersForm from "./UsersForm";
import moment from 'moment';
export default {
  name: "List",
  data() {
    return {
      filter: {
        search: '', // 搜索过滤字段
      },
      totalUsers: 0, // 总用户数
      usersList: [], // 当前页用户数据
      loading: true, // 是否在加载中
      pagination: {}, // 分页信息
      headers: [
        {text: 'id', align: 'center', sortable: false, value: 'id'},
        {text: '用户名', align: 'center', sortable: false, value: 'username'},
        {text: '电话', align: 'center', sortable: false, value: 'phone'},
        {text: '创建时间', align: 'center', value: 'created', sortable: false,},
        {text: '操作', align: 'center', sortable: false}
      ],
      show: false,// 控制对话框的显示
      oldUsers: {}, // 即将被编辑的商品信息
      isEdit: false, // 是否是编辑
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
      this.$http.get("/user/page", {
        params: {
          key: this.filter.search, // 搜索条件
          page: this.pagination.page,// 当前页
          rows: this.pagination.rowsPerPage,// 每页大小
        }
      }).then(resp => { // 这里使用箭头函数
        this.usersList = resp.data.items;
        this.usersList.forEach(user=>{
          user.created = moment(user.created).format("YYYY-MM-DD");
        })
        this.totalUsers = resp.data.total;
        // 完成赋值后，把加载状态赋值为false
        this.loading = false;
      })
    },
    addUser() {
      // 修改标记
      this.isEdit = false;
      // 控制弹窗可见：
      this.show = true;
      // 把oldBrand变为null
      this.oldUsers = {};
    },
    editUsers(oldUsers) {
      // 修改标记
      this.isEdit = true;
      // 控制弹窗可见：
      this.show = true;
      // 获取要编辑的goods
      this.oldUsers = oldUsers;
    },
    closeWindow() {
      console.log(1)
      // 重新加载数据
      this.getDataFromServer();
      //关闭窗口
      this.show = false;
    },
  },
  components: {
    UsersForm,
  }
}
</script>

<style scoped>

</style>
