<template>

  <div id="app">
    <!-- NavCom : 머릿말(메뉴) -->
    <NavCom />

    <!-- 본문(main/section) -->
    <router-view />

    <!-- footerCom : 꼬리말-->
    <FooterCom />
  </div>
</template>

<script>
import FooterCom from "./components/common/FooterCom.vue";
import NavCom from "./components/common/NavCom.vue";

/* eslint-disable */
export default {
  components: {
    NavCom,
    FooterCom,
  },
};
</script>

<style lang="scss">
// HomeView로(자식컴포넌트) css 는 전달됨. js는 안돼서 각각 걸어야 함!!
@import "./assets/css/style.css";
</style>
