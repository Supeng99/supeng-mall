import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Layout from '../views/Layout.vue'
import Dashboard from '../views/Dashboard.vue'
import ProductList from '../views/ProductList.vue'
import CategoryList from '../views/CategoryList.vue'
import OrderList from '../views/OrderList.vue'
import UserList from '../views/UserList.vue'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: Dashboard
      },
      {
        path: 'products',
        name: 'ProductList',
        component: ProductList
      },
      {
        path: 'categories',
        name: 'CategoryList',
        component: CategoryList
      },
      {
        path: 'orders',
        name: 'OrderList',
        component: OrderList
      },
      {
        path: 'users',
        name: 'UserList',
        component: UserList
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
