import request from '@/utils/request'
export default {
  //登录
  submitLoginUser(userInfo){
    return request({
        url: `/educenter/ucenter-member/login`,
        method: 'post',
        data: userInfo
      })
  },


  //根据token获取用户信息
  getLoginUserInfo(){
    return request({
        url: `/educenter/ucenter-member/getMemberInfo`,
        method: 'get'
      })
  }
}