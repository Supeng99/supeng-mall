<template>
  <div class="product-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>Product Management</span>
          <el-button type="primary" @click="handleAdd">Add Product</el-button>
        </div>
      </template>

      <!-- 搜索栏 -->
      <el-form :inline="true" style="margin-bottom: 16px">
        <el-form-item label="Keyword">
          <el-input v-model="searchKeyword" placeholder="Search by name..." clearable style="width: 200px" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadProducts">Search</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="products" border style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="Product Name" />
        <el-table-column prop="subtitle" label="Subtitle" />
        <el-table-column prop="price" label="Price" width="120">
          <template #default="{ row }">
            ¥{{ row.price }}
          </template>
        </el-table-column>
        <el-table-column prop="stock" label="Stock" width="100" />
        <el-table-column prop="status" label="Status" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? 'Active' : 'Inactive' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="Actions" width="200">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">Edit</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">Delete</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="currentPage"
        :page-size="pageSize"
        :total="total"
        :current-page="currentPage"
        layout="total, prev, pager, next"
        style="margin-top: 20px; justify-content: flex-end"
        @current-change="loadProducts"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="Product Name">
          <el-input v-model="form.name" placeholder="Enter product name" />
        </el-form-item>
        <el-form-item label="Subtitle">
          <el-input v-model="form.subtitle" placeholder="Enter subtitle" />
        </el-form-item>
        <el-form-item label="Category">
          <el-select v-model="form.categoryId" placeholder="Select category" style="width: 100%">
            <el-option label="Electronics" :value="1" />
            <el-option label="Clothing" :value="2" />
            <el-option label="Books" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="Price">
          <el-input-number v-model="form.price" :min="0" :precision="2" style="width: 100%" />
        </el-form-item>
        <el-form-item label="Stock">
          <el-input-number v-model="form.stock" :min="0" style="width: 100%" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="handleSave">Save</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import request from '@/utils/request'

const products = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const loading = ref(false)
const dialogVisible = ref(false)
const dialogTitle = ref('Add Product')
const isEdit = ref(false)
const searchKeyword = ref('')

const form = reactive({
  id: null,
  name: '',
  subtitle: '',
  categoryId: null,
  price: 0,
  stock: 0
})

const handleAdd = () => {
  dialogTitle.value = 'Add Product'
  isEdit.value = false
  Object.keys(form).forEach(key => {
    if (key === 'price' || key === 'stock') {
      form[key] = 0
    } else if (key === 'categoryId') {
      form[key] = null
    } else {
      form[key] = ''
    }
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = 'Edit Product'
  isEdit.value = true
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSave = async () => {
  if (!form.name) {
    ElMessage.warning('Product name is required')
    return
  }
  try {
    if (isEdit.value) {
      await request.put('/api/product/update', null, { params: { ...form } })
      ElMessage.success('Updated successfully')
    } else {
      await request.post('/api/product/create', null, { params: { ...form } })
      ElMessage.success('Added successfully')
    }
    dialogVisible.value = false
    loadProducts()
  } catch (e) {
    // error handled by interceptor
  }
}

const handleDelete = (row) => {
  ElMessageBox.confirm('Are you sure to delete this product?', 'Warning', {
    confirmButtonText: 'Confirm',
    cancelButtonText: 'Cancel',
    type: 'warning'
  }).then(async () => {
    try {
      await request.delete(`/api/product/delete/${row.id}`)
      ElMessage.success('Deleted successfully')
      loadProducts()
    } catch (e) {}
  }).catch(() => {})
}

const loadProducts = async () => {
  loading.value = true
  try {
    const res = await request.get('/api/product/list', {
      params: { pageNum: currentPage.value, pageSize: pageSize.value, keyword: searchKeyword.value }
    })
    products.value = res.data.records
    total.value = res.data.total
  } catch (e) {
    // error handled by interceptor
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadProducts()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
