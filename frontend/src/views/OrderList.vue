<template>
  <div class="order-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>Order Management</span>
          <!-- 搜索栏 -->
          <el-form :inline="true">
            <el-form-item>
              <el-input v-model="searchKeyword" placeholder="Search by order no. or receiver..." clearable style="width: 200px" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="loadOrders">Search</el-button>
            </el-form-item>
          </el-form>
        </div>
      </template>

      <el-table :data="orders" border style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="orderNo" label="Order No." width="180" />
        <el-table-column prop="userId" label="User ID" width="100" />
        <el-table-column prop="totalPrice" label="Total" width="120">
          <template #default="{ row }">
            ¥{{ row.totalPrice }}
          </template>
        </el-table-column>
        <el-table-column prop="receiverName" label="Receiver" width="120" />
        <el-table-column prop="receiverPhone" label="Phone" width="130" />
        <el-table-column prop="orderStatus" label="Status" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.orderStatus)">
              {{ getStatusText(row.orderStatus) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="Create Time" width="170">
          <template #default="{ row }">
            {{ row.createTime ? row.createTime.replace('T', ' ').slice(0, 16) : '-' }}
          </template>
        </el-table-column>
        <el-table-column label="Actions" width="180">
          <template #default="{ row }">
            <el-button size="small" @click="handleDetail(row)">Detail</el-button>
            <el-button
              size="small"
              type="primary"
              v-if="row.orderStatus === 1"
              @click="handleShip(row)"
            >
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
        @current-change="loadOrders"
      />
    </el-card>

    <!-- 订单详情弹窗 -->
    <el-dialog v-model="detailVisible" title="Order Detail" width="600px">
      <el-descriptions :column="2" border v-if="currentOrder">
        <el-descriptions-item label="Order No.">{{ currentOrder.orderNo }}</el-descriptions-item>
        <el-descriptions-item label="User ID">{{ currentOrder.userId }}</el-descriptions-item>
        <el-descriptions-item label="Total">¥{{ currentOrder.totalPrice }}</el-descriptions-item>
        <el-descriptions-item label="Status">
          <el-tag :type="getStatusType(currentOrder.orderStatus)">
            {{ getStatusText(currentOrder.orderStatus) }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="Receiver">{{ currentOrder.receiverName }}</el-descriptions-item>
        <el-descriptions-item label="Phone">{{ currentOrder.receiverPhone }}</el-descriptions-item>
        <el-descriptions-item label="Address" :span="2">{{ currentOrder.shippingAddress }}</el-descriptions-item>
        <el-descriptions-item label="Pay Type">{{ currentOrder.payType === 1 ? 'WeChat' : currentOrder.payType === 2 ? 'Alipay' : '-' }}</el-descriptions-item>
        <el-descriptions-item label="Pay Status">
          <el-tag :type="currentOrder.payStatus === 1 ? 'success' : 'warning'">
            {{ currentOrder.payStatus === 1 ? 'Paid' : 'Unpaid' }}
          </el-tag>
        </el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import request from '@/utils/request'

const orders = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const loading = ref(false)
const searchKeyword = ref('')
const detailVisible = ref(false)
const currentOrder = ref(null)

const getStatusType = (status) => {
  const types = { 0: 'warning', 1: 'success', 2: 'primary', 3: 'info', 4: 'danger' }
  return types[status] || 'info'
}

const getStatusText = (status) => {
  const texts = { 0: 'Pending', 1: 'Paid', 2: 'Shipped', 3: 'Completed', 4: 'Cancelled' }
  return texts[status] || 'Unknown'
}

const handleDetail = async (row) => {
  try {
    const res = await request.get(`/api/order/detail/${row.id}`)
    currentOrder.value = res.data
    detailVisible.value = true
  } catch (e) {}
}

const handleShip = async (row) => {
  try {
    await request.put('/api/order/status', null, { params: { id: row.id, orderStatus: 2 } })
    ElMessage.success('Shipped successfully')
    loadOrders()
  } catch (e) {}
}

const loadOrders = async () => {
  loading.value = true
  try {
    const res = await request.get('/api/order/list', {
      params: { pageNum: currentPage.value, pageSize: pageSize.value, keyword: searchKeyword.value }
    })
    orders.value = res.data.records
    total.value = res.data.total
  } catch (e) {
  } finally {
    loading.value = false
  }
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
