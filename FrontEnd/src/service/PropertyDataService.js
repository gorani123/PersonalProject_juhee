// PropertyDataService.js
import http from "../http-common";

class PropertyDataService {
  // 함수명 : upload() 함수 ( DB 저장 함수 )
  // 매개변수 title : 제목
  //          Name : 내용
  //          Property : 업로드 대상 갤러리가 저장되는 객체 배열
  upload(propertyTitle, property, propertyPrice, propertyAddress, propertyCity, propertyBed, propertyBath ) {    // 😦
    //                                객체👀
    // json 객체 사용하지 않음 (x)
    // form 태그로 전송하는 방식을 이용함
    // html <form></form> == js FormData 객체로 사용가능
    //                       .append("속성명", 값) 함수 : 데이터를 저장
    // axios 함수 : .post()
    // 헤더 : "Name-Type" : "multipart/form-data" 
    let formData = new FormData(); // 폼(form) 객체 생성🥶

    formData.append("propertyTitle", propertyTitle);
    formData.append("property", property);
    formData.append("propertyPrice", propertyPrice);
    formData.append("propertyAddress", propertyAddress);
    formData.append("propertyCity", propertyCity);
    formData.append("propertyBed", propertyBed);
    formData.append("propertyBath", propertyBath);

    // 😡
    return http.post("/property/upload", formData, {
        headers: {
            "Content-Type" : "multipart/form-data"
        }
    })
}

  // 이미지 제목 검색 요청 함수🤩
  getPropertys(searchSelect, searchKeyword, page, size) {
    console.log(searchSelect);
    console.log(searchKeyword);
    console.log(page);
    console.log(size);
    // alert(propertyTitle);
    // alert(page);
    // alert(size);

    // this.propertyTitle = "매물";
    return http.get(
      `/property?searchSelect=${searchSelect}&searchKeyword=${searchKeyword}&page=${page}&size=${size}`
    );
  }

  // 이미지 삭제 요청 함수
  delete(pno) {
    return http.delete(`/property/deletion/${pno}`);
  }
}

export default new PropertyDataService();
