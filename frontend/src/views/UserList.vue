<template>
  <div class="user-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>User Management</span>
        </div>
      </template>
      
      <el-table :data="users" border style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="Username" />
        <el-table-column prop="nickname" label="Nickname" />
        <el-table-column prop="email" label="Email" />
        <el-table-column prop="phone" label="Phone" width="150" />
        <el-table-column prop="status" label="Status" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? 'Active' : 'Disabled' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="Create Time" width="180" />
        <el-table-column label="Actions" width="150">
          <template #default="{ row }">
            <el-button size="small" @click="handleDetail(row)">Detail</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'

const users = ref([])

const handleDetail = (row) => {
  ElMessage.info('User detail: ' + row.username)
}

const loadUsers = () => {
  users.value = [
    { id: 1, username: 'user1', nickname: 'User One', email: 'user1@example.com', phone: '13800138000', status: 1, createTime: '2024-01-01 10:00:00' },
    { id: 2, username: 'user2', nickname: 'User Two', email: 'user2@example.com', phone: '13800138001', status: 1, createTime: '2024-01-02 11:00:00' },
    { id: 3, username: 'user3', nickname: 'User Three', email: 'user3@example.com', phone: '13800138002', status: 0, createTime: '2024-01-03 12:00:00' }
  ]
}

onMounted(() => {
  loadUsers()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
