<template>
  <div class="order-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>Order Management</span>
        </div>
      </template>
      
      <el-table :data="orders" border style="width: 100%">
        <el-table-column prop="orderNo" label="Order No." width="180" />
        <el-table-column prop="userId" label="User ID" width="100" />
        <el-table-column prop="totalPrice" label="Total" width="120">
          <template #default="{ row }">
            ¥{{ row.totalPrice }}
          </template>
        </el-table-column>
        <el-table-column prop="orderStatus" label="Status" width="120">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.orderStatus)">
              {{ getStatusText(row.orderStatus) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="Create Time" width="180" />
        <el-table-column label="Actions" width="200">
          <template #default="{ row }">
            <el-button size="small" @click="handleDetail(row)">Detail</el-button>
            <el-button size="small" type="primary" v-if="row.orderStatus === 1" @click="handleShip(row)">
              Ship
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <el-pagination
        v-model:current-page="currentPage"
        :page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'

const orders = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const getStatusType = (status) => {
  const types = { 0: 'warning', 1: 'success', 2: 'primary', 3: 'info', 4: 'danger' }
  return types[status] || 'info'
}

const getStatusText = (status) => {
  const texts = { 0: 'Pending', 1: 'Paid', 2: 'Shipped', 3: 'Completed', 4: 'Cancelled' }
  return texts[status] || 'Unknown'
}

const handleDetail = (row) => {
  ElMessage.info('Order detail: ' + row.orderNo)
}

const handleShip = (row) => {
  ElMessage.success('Order shipped: ' + row.orderNo)
}

const loadOrders = () => {
  orders.value = [
    { orderNo: 'ORD20240101001', userId: 1, totalPrice: 8999, orderStatus: 1, createTime: '2024-01-01 10:00:00' },
    { orderNo: 'ORD20240101002', userId: 2, totalPrice: 1899, orderStatus: 2, createTime: '2024-01-01 11:00:00' },
    { orderNo: 'ORD20240101003', userId: 3, totalPrice: 29999, orderStatus: 3, createTime: '2024-01-01 12:00:00' }
  ]
  total.value = 3
}

onMounted(() => {
  loadOrders()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
