"use strict";(self["webpackChunkfront_end"]=self["webpackChunkfront_end"]||[]).push([[921],{1921:function(e,t,a){a.r(t),a.d(t,{default:function(){return u}});var s=function(){var e=this,t=e._self._c;return t("div",[t("div",{staticClass:"col-md-8"},[t("div",{staticClass:"input-group mb-3"},[t("input",{directives:[{name:"model",rawName:"v-model",value:e.searchEmail,expression:"searchEmail"}],staticClass:"form-control",attrs:{type:"text",placeholder:"Search by Email"},domProps:{value:e.searchEmail},on:{input:function(t){t.target.composing||(e.searchEmail=t.target.value)}}}),t("div",{staticClass:"input-group-append"},[t("button",{staticClass:"btn btn-outline-secondary",attrs:{type:"button"},on:{click:function(t){e.page=1,e.retrieveCustomer()}}},[e._v(" Search ")])])])]),t("div",{staticClass:"col-md-12"},[t("div",{staticClass:"mb-3"},[e._v(" Items per Page: "),t("select",{directives:[{name:"model",rawName:"v-model",value:e.pageSize,expression:"pageSize"}],on:{change:[function(t){var a=Array.prototype.filter.call(t.target.options,(function(e){return e.selected})).map((function(e){var t="_value"in e?e._value:e.value;return t}));e.pageSize=t.target.multiple?a:a[0]},function(t){return e.handlePageSizeChange(t)}]}},e._l(e.pageSizes,(function(a){return t("option",{key:a,domProps:{value:a}},[e._v(" "+e._s(a)+" ")])})),0)]),t("b-pagination",{attrs:{"total-rows":e.count,"per-page":e.pageSize,"prev-text":"Prev","next-text":"Next"},on:{change:e.handlePageChange},model:{value:e.page,callback:function(t){e.page=t},expression:"page"}})],1),t("table",{staticClass:"table"},[e._m(0),e._l(e.customer,(function(a,s){return t("tbody",{key:s},[t("tr",[t("td",[e._v(e._s(a.lastName+" "+a.firstName))]),t("td",[e._v(e._s(a.email))]),t("td",[e._v(e._s(a.phone))]),t("td",[t("router-link",{attrs:{to:"/customer/"+a.cid}},[t("span",{staticClass:"badge bg-success"},[e._v("Edit")])])],1)])])}))],2)])},r=[function(){var e=this,t=e._self._c;return t("thead",[t("tr",[t("th",{attrs:{scope:"col"}},[e._v("Name")]),t("th",{attrs:{scope:"col"}},[e._v("Email")]),t("th",{attrs:{scope:"col"}},[e._v("Phone")]),t("th",{attrs:{scope:"col"}},[e._v("Actions")])])])}],n=a(6053),o={data(){return{customer:[],searchEmail:"",page:1,count:0,pageSize:3,pageSizes:[3,6,9]}},methods:{retrieveCustomer(){n.Z.getAll(this.searchEmail,this.page-1,this.pageSize).then((e=>{const{customer:t,totalItems:a}=e.data;this.customer=t,this.count=a,console.log(e.data)})).catch((e=>{console.log(e)}))},handlePageSizeChange(e){this.pageSize=e.target.value,this.page=1,this.retrieveCustomer()},handlePageChange(e){this.page=e,this.retrieveCustomer()}},mounted(){this.retrieveCustomer()}},i=o,l=a(1001),c=(0,l.Z)(i,s,r,!1,null,null,null),u=c.exports},6053:function(e,t,a){var s=a(6645);class r{getAll(e,t,a){return s.Z.get(`/customer?email=${e}&page=${t}&size=${a}`)}get(e){return s.Z.get(`/customer/${e}`)}create(e){return console.log(e),s.Z.post("/customer",e)}update(e,t){return s.Z.put(`/customer/${e}`,t)}delete(e){return s.Z["delete"](`/customer/deletion/${e}`)}}t["Z"]=new r}}]);
//# sourceMappingURL=921.2c5f51aa.js.map