<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-value">{{ stats.totalProducts }}</div>
            <div class="stat-label">Total Products</div>
          </div>
          <el-icon class="stat-icon" color="#409EFF"><Goods /></el-icon>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-value">{{ stats.totalOrders }}</div>
            <div class="stat-label">Total Orders</div>
          </div>
          <el-icon class="stat-icon" color="#67C23A"><Document /></el-icon>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-value">{{ stats.totalUsers }}</div>
            <div class="stat-label">Total Users</div>
          </div>
          <el-icon class="stat-icon" color="#E6A23C"><User /></el-icon>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-value">¥{{ stats.totalSales }}</div>
            <div class="stat-label">Total Sales</div>
          </div>
          <el-icon class="stat-icon" color="#F56C6C"><Money /></el-icon>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { Goods, Document, User, Money } from '@element-plus/icons-vue'

const stats = ref({
  totalProducts: 156,
  totalOrders: 89,
  totalUsers: 234,
  totalSales: 45678.90
})

onMounted(() => {
  // Load actual stats from API
})
</script>

<style scoped>
.dashboard {
  padding: 20px;
}

.stat-card {
  position: relative;
  overflow: hidden;
}

.stat-content {
  display: inline-block;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 8px;
}

.stat-label {
  font-size: 14px;
  color: #909399;
}

.stat-icon {
  position: absolute;
  right: 20px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 48px;
  opacity: 0.3;
}
</style>
