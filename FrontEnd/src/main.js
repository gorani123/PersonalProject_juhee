import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

// bootstrap-vue import (페이징 처리) 부트스트랩 추가설치
import { BootstrapVue } from 'bootstrap-vue'
import 'bootstrap-vue/dist/bootstrap-vue.css'



// TODO: login 예제 라이브러리 import 필요
// npm i vee-validate@2.2.15
// npm i @fortawesome/fontawesome-svg-core
// npm i @fortawesome/free-solid-svg-icons
// npm i @fortawesome/vue-fontawesome
// 유효성 체크 라이브러리 : vee-validate ( Vue 전용 )
import VeeValidate from 'vee-validate';

// fortawesome 아이콘들
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
// faHome( 홈아이콘 ), faUser(유저아이콘) 등
import {
  faHome,
  faUser,
  faUserPlus,
  faSignInAlt,
  faSignOutAlt
} from '@fortawesome/free-solid-svg-icons';

library.add(faHome, faUser, faUserPlus, faSignInAlt, faSignOutAlt);

Vue.use(VeeValidate);
Vue.component('font-awesome-icon', FontAwesomeIcon);



// bootstrap-vue 모듈 사용
Vue.use(BootstrapVue)

Vue.config.productionTip = false


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
