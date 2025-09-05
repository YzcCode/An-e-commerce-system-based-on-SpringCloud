<template>
  <v-card>
    <v-flex xs12 sm10>
      <v-tree url="/item/category/list"
              :isEdit="isEdit"
              @handleAdd="handleAdd"
              @handleEdit="handleEdit"
              @handleDelete="handleDelete"
              @handleClick="handleClick"
      />
    </v-flex>
  </v-card>
</template>

<script>
  import {treeData} from "../../mockDB";

  export default {
    name: "category",
    data() {
      return {
        isEdit:true,
        treeData
      }
    },
    methods: {
      handleAdd(node) {
        this.$http({
          method: "post",
          url: "/item/category",
          data: node
        })
          .then(() => {
            this.$message.success("添加成功");
          })
          .catch(() => {
            this.$message.error("添加失败！");
          });
      },
      handleEdit(id, name) {
        let data = {};
        data.id = id;
        data.name = name;
        this.$http({
          method: "put",
          url: "/item/category",
          data: data
        })
          .then(() => {
            this.$message.success("修改成功");
          })
          .catch(() => {
            this.$message.error("修改失败！");
          });

        console.log("edit... id: " + id + ", name: " + name)
      },
      handleDelete(id) {
        this.$http.delete("/item/category/" + id)
          .then(() => {
            this.$message.success("删除成功");
          })
          .catch(() => {
            this.$message.error("删除失败！");
          });
        console.log("delete ... " + id)
      },
      handleClick(node) {
        console.log(node)
      }
    }
  };
</script>

<style scoped>

</style>
