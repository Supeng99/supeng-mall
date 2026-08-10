<template>
  <div class="category-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>Category Management</span>
          <el-button type="primary" @click="handleAdd">Add Category</el-button>
        </div>
      </template>
      
      <el-table :data="categories" border style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="Category Name" />
        <el-table-column prop="parentId" label="Parent ID" width="120" />
        <el-table-column prop="sort" label="Sort Order" width="120" />
        <el-table-column label="Actions" width="150">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">Edit</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">Delete</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'

const categories = ref([])

const handleAdd = () => {
  ElMessage.info('Add category dialog')
}

const handleEdit = (row) => {
  ElMessage.info('Edit category: ' + row.name)
}

const handleDelete = (row) => {
  ElMessage.success('Deleted: ' + row.name)
}

const loadCategories = () => {
  categories.value = [
    { id: 1, name: 'Electronics', parentId: 0, sort: 1 },
    { id: 2, name: 'Clothing', parentId: 0, sort: 2 },
    { id: 3, name: 'Books', parentId: 0, sort: 3 },
    { id: 4, name: 'Smartphones', parentId: 1, sort: 1 },
    { id: 5, name: 'Laptops', parentId: 1, sort: 2 }
  ]
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
