<template>
  <div class="product-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>Product Management</span>
          <el-button type="primary" @click="handleAdd">Add Product</el-button>
        </div>
      </template>
      
      <el-table :data="products" border style="width: 100%">
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
        layout="total, prev, pager, next"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </el-card>
    
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="Product Name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="Subtitle">
          <el-input v-model="form.subtitle" />
        </el-form-item>
        <el-form-item label="Category">
          <el-select v-model="form.categoryId" placeholder="Select category">
            <el-option label="Electronics" :value="1" />
            <el-option label="Clothing" :value="2" />
            <el-option label="Books" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="Price">
          <el-input-number v-model="form.price" :min="0" :precision="2" />
        </el-form-item>
        <el-form-item label="Stock">
          <el-input-number v-model="form.stock" :min="0" />
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

const products = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const dialogVisible = ref(false)
const dialogTitle = ref('Add Product')
const isEdit = ref(false)

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
    if (key !== 'price' && key !== 'stock') {
      form[key] = key === 'categoryId' ? null : ''
    } else {
      form[key] = 0
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

const handleSave = () => {
  ElMessage.success(isEdit.value ? 'Updated successfully' : 'Added successfully')
  dialogVisible.value = false
  loadProducts()
}

const handleDelete = (row) => {
  ElMessageBox.confirm('Are you sure to delete this product?', 'Warning', {
    confirmButtonText: 'Confirm',
    cancelButtonText: 'Cancel',
    type: 'warning'
  }).then(() => {
    ElMessage.success('Deleted successfully')
    loadProducts()
  })
}

const loadProducts = () => {
  products.value = [
    { id: 1, name: 'iPhone 15 Pro', subtitle: 'Latest Apple Phone', price: 8999, stock: 100, status: 1 },
    { id: 2, name: 'MacBook Pro 14', subtitle: 'M3 Pro Chip', price: 16999, stock: 50, status: 1 },
    { id: 3, name: 'AirPods Pro', subtitle: 'Wireless Earbuds', price: 1899, stock: 200, status: 1 }
  ]
  total.value = 3
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
