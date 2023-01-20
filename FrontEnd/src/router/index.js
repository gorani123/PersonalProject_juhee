import Vue from 'vue'
import VueRouter from 'vue-router'
// import HomeView from '../views/HomeView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: () => import('../views/HomeView.vue')
  },
  {
    path: '/about',
    name: 'about',
    component: () => import('../components/aboutCom.vue')
  },
  {
    path: '/contact',
    name: 'contact',
    component: () => import('../components/contactCom.vue')
  },
  {
    path: '/propertysingle',
    name: 'property-single',
    component: () => import('../components/propertySingleCom.vue')
  },
  {
    path: '/services',
    name: 'services',
    component: () => import('../components/servicesCom.vue')
  },
  {
    path: '/addqna',
    name: 'add-qna',
    component: () => import('../components/AddQna.vue')
  },
  {
    path: '/qna',
    name: 'qna',
    component: () => import('../components/QnaList.vue')
  },
  {
    path: '/qna/:qno',
    name: 'qna-detail',
    component: () => import('../components/QnaDetail.vue')
  },
  {
    path: '/buy',
    name: 'buy-property',
    component: () => import('../components/property/buyProperty.vue')
  },
  {
    path: '/sell',
    name: 'sell-property',
    component: () => import('../components/property/sellProperty.vue')
  },
   // TODO: 로그인 메뉴 달기
   {
    path: '/login',
    name: 'login',
    component: () => import('../views/LoginView.vue')
  },  
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/RegisterView.vue')
  },  
  {
    path: '/profile',
    name: 'profile',
    component: () => import('../views/ProfileView.vue')
  },  
  // TODO: admin 메뉴 추가
  {
    path: '/user',
    name: 'user',
    component: () => import('../components/admin/UserList.vue')
  },    
  {
    path: '/add-user',
    name: 'add-user',
    component: () => import('../components/admin/AddUser.vue')
  },  
  {
    path: '/user/:id',
    name: 'user-detail',
    component: () => import('../components/admin/UserDetail.vue')
  },  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
