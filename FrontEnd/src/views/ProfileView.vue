<template>
  <div>
    <!-- 상단바 양식 -->
    <div
      class="hero page-inner overlay"
      style="background-image: url('images/hero_bg_3.jpg')"
    >
      <div class="container">
        <div class="row justify-content-center align-items-center">
          <div class="col-lg-9 text-center mt-5">
            <h1 class="heading" data-aos="fade-up">Profile</h1>
            
            <nav
              aria-label="breadcrumb"
              data-aos="fade-up"
              data-aos-delay="200"
            >
              <ol class="breadcrumb text-center justify-content-center">
                <li class="breadcrumb-item"><a href="index.html">Home</a></li>
                <li
                  class="breadcrumb-item active text-white-50"
                  aria-current="page"
                >
                  My Profile
                </li>
              </ol>
            </nav>
          </div>
        </div>
      </div>
    </div>
    <!-- 상단바양식 끝 -->

    <div class="container">
      <header class="jumbotron">
        <h3>
          <strong>{{ currentUser.username }}</strong> Profile
        </h3>
      </header>
      <p>
        <strong>Token:</strong>
        {{ currentUser.accessToken.substring(0, 20) }} …
        {{
          currentUser.accessToken.substr(currentUser.accessToken.length - 20)
        }}
      </p>
      <p>
        <strong>Id:</strong>
        {{ currentUser.id }}
      </p>
      <p>
        <strong>Email:</strong>
        {{ currentUser.email }}
      </p>
      <strong>Authorities:</strong>
      <ul>
        <li v-for="(role, index) in currentUser.roles" :key="index">
          {{ role }}
        </li>
      </ul>
    </div>
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
  },
  // 화면이 뜨자마자 실행되는 이벤트
  mounted() {
    if (!this.currentUser) {
      this.$router.push("/login"); // user 키가 없으면 강제 /login 페이지 이동
    }
  },
};
</script>

<style></style>
