import request from '@/utils/request'
export default {
  //根据手机号发送验证码
  getTeacherList(page,limit) {
    return request({
      url: `/eduservice/teacherfront/getTeacherFrontList/${page}/${limit}`,
      method: 'post'
    })
  },

  //注册
  getTeacherInfo(id){
    return request({
        url: `/eduservice/teacherfront/getTeacherFrontInfo/${id}`,
        method: 'get'
      })
  }
}