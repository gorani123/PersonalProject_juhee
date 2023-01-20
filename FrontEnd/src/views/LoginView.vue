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
            <h1 class="heading" data-aos="fade-up">Log In</h1>

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
                  Upload property
                </li>
              </ol>
            </nav>
          </div>
        </div>
      </div>
    </div>
    <!-- 상단바양식 끝 -->

    <div class="col-md-12">
      <div class="card card-container">
        <img
          id="profile-img"
          src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
          class="profile-img-card"
        />
        <form name="form" @submit.prevent="handleLogin">
          <div class="form-group">
            <label for="username">Username</label>
            <!-- v-validate : vee-validate 라이브러리 제공하는 속성 -->
            <!-- vee-validate : vue 전용 유효성 체크 라이브러리 -->
            <!-- v-validate="유효성속성1 | 유효성속성2 | ..."  -->
            <!--    에러발생시 : errors.has('username') -> true 아래 메세지가 보임 -->
            <input
              v-model="user.username"
              v-validate="'required'"
              type="text"
              class="form-control"
              name="username"
            />
            <div
              v-if="errors.has('username')"
              class="alert alert-danger"
              role="alert"
            >
              Username is required!
            </div>
          </div>
          <div class="form-group">
            <label for="password">Password</label>
            <input
              v-model="user.password"
              v-validate="'required'"
              type="password"
              class="form-control"
              name="password"
            />
            <div
              v-if="errors.has('password')"
              class="alert alert-danger"
              role="alert"
            >
              Password is required!
            </div>
          </div>
          <!-- 버튼 시작 -->
          <div class="form-group mt-3">
            <button class="btn btn-primary btn-block" :disabled="loading">
              <span
                v-show="loading"
                class="spinner-border spinner-border-sm"
              ></span>
              <span>Login</span>
            </button>
          </div>
          <!-- 버튼 끝 -->
          <div class="form-group">
            <div v-if="message" class="alert alert-danger" role="alert">
              {{ message }}
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import User from "../model/user.js";

export default {
  data() {
    return {
      user: new User("", ""), // user 생성자 호출
      loading: false, // 로그인 버튼 활성화/비활성화 속성
      message: "",
    };
  },
  computed: {
    // loggedIn 상태 확인
    loggedIn() {
      // this.$store.state.모듈명.state값
      return this.$store.state.auth.status.loggedIn;
    },
  },
  // created() : Vue 생성되자 마자 실행되는 이벤트(화면은 생성되지 않은 상태)
  created() {
    if (this.loggedIn) {
      // 로그인이 되어 있는 상태
      this.$router.push("/profile"); // 강제 /profile 로 이동
    }
  },
  methods: {
    // 로그인 버튼 클릭시 실행되는 함수
    handleLogin() {
      // 로그인 로직 처리
      this.loading = true;
      // vee-validate 함수 처리 방법
      this.$validator.validateAll().then((isValid) => {
        // validateAll() 모두 통과하면 -> isValid = true  (유효함)
        //                             -> isValid = false (유효하지 않음)
        if (!isValid) {
          this.loading = false;
          return; // 함수 탈출(break)
        }

        // isValid 가 true 이면 아래 로그인 실행(axios 실행)
        if (this.user.username && this.user.password) {
          // 공통함수 login 호출 :
          // this.$store.dispatch("모듈명/함수명", 매개변수)
          this.$store
            .dispatch("auth/login", this.user)
            .then(() => {
              this.$router.push("/profile"); // 로그인 성공하면 강제 /profile 페이지 이동
            })
            // 참고) if/else 문 대신에 -> or(||) and(&&) 연산자를 사용할때도 있음
            // 로직체크 순서 : true || false, false && true
            .catch((error) => {
              this.loading = false; // 로그인 버튼 활성화
              this.message =
                (error.response &&
                  error.response.data &&
                  error.response.data.message) ||
                error.message ||
                error.toString();
            });
        }
      });
    },
  },
};
</script>

<style scoped>
label {
  display: block;
  margin-top: 10px;
}

.card-container.card {
  max-width: 350px !important;
  padding: 40px 40px;
}

.card {
  background-color: #f7f7f7;
  padding: 20px 25px 30px;
  margin: 0 auto 25px;
  margin-top: 50px;
  -moz-border-radius: 2px;
  -webkit-border-radius: 2px;
  border-radius: 2px;
  -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
}

.profile-img-card {
  width: 96px;
  height: 96px;
  margin: 0 auto 10px;
  display: block;
  -moz-border-radius: 50%;
  -webkit-border-radius: 50%;
  border-radius: 50%;
}
</style>
