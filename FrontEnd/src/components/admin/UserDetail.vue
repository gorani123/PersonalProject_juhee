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
            <h3 class="heading" data-aos="fade-up">Edit</h3>

            

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
                  Edit User
                </li>
              </ol>
            </nav>
          </div>
        </div>
      </div>
    </div>
    <!-- 상단바양식 끝 -->
    <!-- 위에nav랑 약간 띄우기. 원본소스 이용  -->
        <!-- <div class="untree_co-section">
      <div class="container" data-aos="fade-left" data-aos-delay="200">
        <div class="row">
          <div class="col-lg-12 mx-auto text-center"> -->


    <div v-if="currentUser" class="container-fluid w-50">
      <div>
        <div class="form-group">
          <label for="username">Username</label>
          <!-- v-validate : 유효성 체크 -->
          <!-- v-validate="'required|min:3|max:20'" 체크함 -->
          <!-- errors.has('username') : 에러 발생했는지 검사 -->
          <!-- {{errors.first('username')}} : 에러 있으면 화면 출력됨, 없으면 안됨 -->
          <input
            v-model="currentUser.username"
            v-validate="'required|min:3|max:20'"
            type="text"
            class="form-control"
            name="username"
          />
          <!-- 유효성 체크 후 에러 체크 : errors.hat(체크대상) -->
          <!-- errors.first(체크대상) : 에러 내용 출력 -->
          <div v-if="submitted && errors.has('username')" class="alert-danger">
            {{ errors.first("username") }}
          </div>
        </div>
        <div class="form-group">
          <label for="email">Email</label>
          <input
            v-model="currentUser.email"
            v-validate="'required|email|max:50'"
            type="email"
            class="form-control"
            name="email"
          />
          <div v-if="submitted && errors.has('email')" class="alert-danger">
            {{ errors.first("email") }}
          </div>
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input
            v-model="currentUser.password"
            type="password"
            class="form-control"
            name="password"
            :disabled="!changePwd"
          />
          <button class="mt-3 btn btn-warning btn-sm" @click="changePassword">
            패스워드 변경
          </button>
        </div>

        <div class="form-group">
          <label for="password">role</label>
          <select class="form-select" v-model="currentUser.role[0].name">
            <option>ROLE_USER</option>
            <option>ROLE_ADMIN</option>
          </select>
        </div>

        <button class="mt-3 btn btn-danger" @click="deleteUser">Delete</button>

        <button
          type="submit"
          class="ms-3 mt-3 btn btn-success"
          @click="updateUser(currentUser.id, changePwd, currentUser)"
        >
          Update
        </button>
        <p>{{ message }}</p>
      </div>
    </div>

    <div v-else>
      <br />
      <p>Please click on a User…</p>
    </div>
  </div>
</template>

<script>
import navbarCom from "../../assets/js/navbar";
import counterCom from "../../assets/js/counter";
import customCom from "../../assets/js/custom";
import UserDataService from "../../service/UserDataService";

export default {
  data() {
    return {
      currentUser: null,
      submitted: false,
      message: "",
      changePwd: false,
    };
  },
  methods: {
    getUser(id) {
      UserDataService.get(id) // spring 요청
        //  성공/실패 모르겠지만
        //  성공하면 then안에 있는것이 실행
        .then((response) => {
          this.currentUser = response.data;
          console.log(response.data);
        })
        //  실패하면 catch안에 있는것이 실행
        .catch((e) => {
          console.log(e);
        });
    },
    updateUser(id, changePwd, user) {
      this.message = "";
      this.submitted = true;
      // form 유효성 체크 검사
      // this.$validator.validate() : 유효하면 isValid = true , 아니면 isValid = false
      this.$validator.validate().then((isValid) => {
        if (isValid) {
          // user 값 초기화
          // this.user = new User("", "", "", this.role);
          //  공유 저장소의 새사용자 등록 함수 실행
          UserDataService.update(id, changePwd, user)
            .then((response) => {
              console.log(response.data);
              // TODO: 수정
              // this.message = "The User was updated successfully!";
              alert("회원정보가 성공적으로 변경되었습니다.")
            })
            .catch((e) => {
              console.log(e);
            });
        }
      });
    },
    deleteUser() {
      UserDataService.delete(this.currentUser.id)
        .then((response) => {
          console.log(response.data);
          alert("회원정보가 안전하게 삭제되었습니다.")
          this.$router.push("/user");
        })
        .catch((e) => {
          console.log(e);
        });
    },
    changePassword() {
      this.currentUser.password = "";
      this.changePwd = true;
    },
  },
  mounted() {
    navbarCom();
    counterCom();
    customCom();
    this.message = "";
    this.getUser(this.$route.params.id);
  },
};
</script>

<style scoped></style>
