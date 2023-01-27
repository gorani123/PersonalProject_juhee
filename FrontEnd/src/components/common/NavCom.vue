<template>
  <div>

    <!-- TODO: 메뉴 양 사이드로 붙이기 수정 -->
  <!-- TODO: 맞는지는 모름.. -->
  <!-- <div class="untree_co-section">
      <div class="container" data-aos="fade-left" data-aos-delay="200">
        <div class="row">
          <div class="col-lg-12 mx-auto text-center"> -->

    <!-- 선생님 방식은 공통폴더인 common 폴더 만들어서 공통으로 사용되는 코드 따로
    파일로 만듦 NavCom.vue 원본 html파일에서 공통부분인 Nav만 여기 적을거임
    뷰에서 a 링크쓰면 외부라이브러리 안 들어 옴. 라우터링크로 수정해야 함

    뷰는 클릭하면 컴포넌트로 전환되는 개념
    a 태그는 클릭하면 파일이 바뀌는 개념
    
    a링크->  routerlink로 수정 -->
    
    <!-- 공통 모바일로 메뉴 적용되는 부분 -->
    <div class="site-mobile-menu site-navbar-target">
      <div class="site-mobile-menu-header">
        <div class="site-mobile-menu-close">
          <span class="icofont-close js-menu-toggle"></span>
        </div>
      </div>
      <div class="site-mobile-menu-body"></div>
    </div>

      <!-- nav -->
      <nav class="site-nav">
      <div class="container">
        <div class="menu-bg-wrap">
          <div class="site-navigation">
            <!-- TODO: float start -> float-left -->
            <router-link to="/" class="logo m-0 float-left">Property</router-link>

          <!-- TODO: float-end -> float-left    _메뉴(home, service 등) -->
            <ul
              class="js-clone-nav d-none d-lg-inline-block text-start site-menu float-left"
            >
              <li class="active"><router-link to="/">Home</router-link></li>
              <li class="has-children">
                <router-link to="/">Properties</router-link>
                <ul class="dropdown">
                  <li><router-link to="/buy">Buy Property</router-link></li>
                  <li><router-link to="/sell">Sell Property</router-link></li>
                  <li class="has-children">
                    <router-link to="/qna">Qna</router-link>
                    <ul class="dropdown">
                      <li><router-link to="/addqna">Add Qna</router-link></li>
                      <li><router-link to="/qna">Qna</router-link></li>
                    </ul>
                  </li>
                </ul>
              </li>
              <li><router-link to="/about">About</router-link></li>
              <li><router-link to="/contact">Contact Us</router-link></li>

              <!-- TODO: 메뉴추가 admin -->
              <li v-if="showAdminBoard" class="has-children">
                <a href="#">Admin</a>
                <ul class="dropdown">
                  <li>
                    <router-link to="/user">User</router-link>
                  </li>
                  <li>
                    <router-link to="/add-user">Add User</router-link>
                  </li>
                </ul>
              </li>
            </ul>

            <!-- todo) 메뉴추가 login , float-right -->
            <!-- login 시작 -->
            <ul
              v-if="!currentUser"
              class="js-clone-nav d-none d-lg-inline-block text-left site-menu float-right"
            >
              <li>
                <router-link to="/register">
                  <font-awesome-icon icon="user-plus" />
                  register</router-link
                >
              </li>
              <li>
                <router-link to="/login"
                  ><font-awesome-icon icon="sign-in-alt" /> login</router-link
                >
              </li>
            </ul>
            <!-- login 끝 -->
            <!-- logout 시작 -->
            <ul
              v-if="currentUser"
              class="js-clone-nav d-none d-lg-inline-block text-left site-menu float-right"
            >
              <li>
                <router-link to="/profile"
                  ><font-awesome-icon icon="user" /> profile</router-link
                >
              </li>
              <li>
                <a href="#" @click.prevent="logout">
                  <font-awesome-icon icon="sign-out-alt" />
                  logout</a
                >
              </li>
            </ul>
            <!-- logout 끝 -->
            <!-- TODO: 메뉴추가 끝 -->

            <router-link to="/"
              class="burger light me-auto float-end mt-1 site-menu-toggle js-menu-toggle d-inline-block d-lg-none"
              data-toggle="collapse"
              data-target="/main-navbar"
            >
              <span></span>
          </router-link>
          </div>
        </div>
      </div>
    </nav>

  </div>
</template>


<script>
export default {
  computed: {
    currentUser() {
      // 모듈 저장소 : this.$store.state.모듈명.state값
      // user 객체 의 속성 : username, password, email, accesToken, roles(배열)
      return this.$store.state.auth.user;
    },
    // ROLE_ADMIN 만 보이는 메뉴(함수)
    showAdminBoard() {
      if (this.currentUser && this.currentUser.roles) {
        // if ROLE_ADMIN 있으면 true
        //               없으면 false
        return this.currentUser.roles.includes("ROLE_ADMIN");
      }
      // currentUser 없으면 false (메뉴가 안보임)
      return false;
    },
  },
  methods: {
    // 로그아웃 함수 -> 공통함수 호출
    logout() {
      // this.$store.dispatch("모듈명/함수명")
      this.$store.dispatch("auth/logout"); // 공통함수 logout 호출
      this.$router.push("/login"); // 강제 /login 페이지로 이동
    },
  },
};
</script>

<style>

</style>