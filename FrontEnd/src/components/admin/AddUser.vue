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
            <h1 class="heading" data-aos="fade-up">관리자 페이지</h1>
            <h3 class="heading" data-aos="fade-up">Register</h3>

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
                  Add User
                </li>
              </ol>
            </nav>
          </div>
        </div>
      </div>
    </div>
    <!-- 상단바양식 끝 -->

    <div class="untree_co-section">
      <div class="container" data-aos="fade-left" data-aos-delay="200">
        <div class="row">
          <div class="col-lg-12 mx-auto">
            <div v-if="!submitted" class="container-fluid w-50">
              <form name="form" @submit.prevent="saveUser">
                <div v-if="!successful">
                  <div class="form-group">
                    <label for="username">Username</label>
                    <!-- v-validate : 유효성 체크 -->
                    <!-- v-validate="'required|min:3|max:20'" 체크함 -->
                    <!-- errors.has('username') : 에러 발생했는지 검사 -->
                    <!-- {{errors.first('username')}} : 에러 있으면 화면 출력됨, 없으면 안됨 -->
                    <input
                      v-model="user.username"
                      v-validate="'required|min:3|max:20'"
                      type="text"
                      class="form-control"
                      name="username"
                    />
                    <!-- 유효성 체크 후 에러 체크 : errors.hat(체크대상) -->
                    <!-- errors.first(체크대상) : 에러 내용 출력 -->
                    <div
                      v-if="submitted && errors.has('username')"
                      class="alert-danger"
                    >
                      {{ errors.first("username") }}
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="email">Email</label>
                    <input
                      v-model="user.email"
                      v-validate="'required|email|max:50'"
                      type="email"
                      class="form-control"
                      name="email"
                    />
                    <div
                      v-if="submitted && errors.has('email')"
                      class="alert-danger"
                    >
                      {{ errors.first("email") }}
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="password">Password</label>
                    <input
                      v-model="user.password"
                      v-validate="'required|min:6|max:40'"
                      type="password"
                      class="form-control"
                      name="password"
                    />
                    <div
                      v-if="submitted && errors.has('password')"
                      class="alert-danger"
                    >
                      {{ errors.first("password") }}
                    </div>
                  </div>

                  <div class="form-group">
                    <label for="password">role</label>&nbsp;
                    <select class="form-select" v-model="user.role">
                      <option>ROLE_USER</option>
                      <option>ROLE_ADMIN</option>
                    </select>
                  </div>

                  <div class="form-group mt-3">
                    <button class="btn btn-primary btn-block">Save</button>
                  </div>
                </div>
              </form>
            </div>

            <div v-else>
              <h4>You submitted successfully!</h4>
              <button class="btn btn-success" @click="newUser">Add</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>



<script>
import navbarCom from "../../assets/js/navbar";
import counterCom from "../../assets/js/counter";
import customCom from "../../assets/js/custom";
import User from "../../model/user";
import UserDataService from "../../service/UserDataService";

export default {
  data() {
    return {
      user: new User("", "", "", "ROLE_USER"),
      submitted: false,
      successful: false,
      message: "",
    };
  },
  methods: {
    saveUser() {
      this.message = "";
      this.submitted = true; // 저장 버튼 누를때 true 변경
      // 유효성 체크
      this.$validator.validate().then((isValid) => {
        if (isValid) {
          UserDataService.create(this.user)
            .then((response) => {
              this.message = response.message; // 서버메세지를 화면에 출력
            })
            .catch((e) => {
              console.log(e);
            });
        }
      });
    },
    newUser() {
      this.submitted = false;
      this.user = new User("", "", "", "ROLE_USER");
    },
  },
    // 화면이 뜨자마자 실행되는 이벤트(라이프 사이클 함수) : mounted(), created()
  mounted() {
    navbarCom();
    counterCom();
    customCom();
  },
};
</script>

<style></style>
