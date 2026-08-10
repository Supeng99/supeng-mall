<template>
  <div class="category-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>Category Management</span>
          <el-button type="primary" @click="handleAdd">Add Category</el-button>
        </div>
      </template>

      <el-table :data="categories" border style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="Category Name" />
        <el-table-column prop="icon" label="Icon" width="120">
          <template #default="{ row }">
            {{ row.icon || '-' }}
          </template>
        </el-table-column>
        <el-table-column label="Actions" width="150">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">Edit</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">Delete</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="Category Name">
          <el-input v-model="form.name" placeholder="Enter category name" />
        </el-form-item>
        <el-form-item label="Icon">
          <el-input v-model="form.icon" placeholder="Icon class or URL (optional)" />
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

const categories = ref([])
const loading = ref(false)
const dialogVisible = ref(false)
const dialogTitle = ref('Add Category')
const isEdit = ref(false)

const form = reactive({
  id: null,
  name: '',
  icon: ''
})

const handleAdd = () => {
  dialogTitle.value = 'Add Category'
  isEdit.value = false
  form.id = null
  form.name = ''
  form.icon = ''
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = 'Edit Category'
  isEdit.value = true
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSave = async () => {
  if (!form.name) {
    ElMessage.warning('Category name is required')
    return
  }
  try {
    if (isEdit.value) {
      await request.put('/api/category/update', null, { params: { id: form.id, name: form.name, icon: form.icon } })
      ElMessage.success('Updated successfully')
    } else {
      await request.post('/api/category/create', null, { params: { name: form.name, icon: form.icon } })
      ElMessage.success('Added successfully')
    }
    dialogVisible.value = false
    loadCategories()
  } catch (e) {}
}

const handleDelete = (row) => {
  ElMessageBox.confirm('Are you sure to delete this category?', 'Warning', {
    confirmButtonText: 'Confirm',
    cancelButtonText: 'Cancel',
    type: 'warning'
  }).then(async () => {
    try {
      await request.delete(`/api/category/delete/${row.id}`)
      ElMessage.success('Deleted successfully')
      loadCategories()
    } catch (e) {}
  }).catch(() => {})
}

const loadCategories = async () => {
  loading.value = true
  try {
    const res = await request.get('/api/category/list')
    categories.value = res.data
  } catch (e) {
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadCategories()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
