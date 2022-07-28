import request from '@/utils/request'
export default {
  //生成订单
  createOrders(courseId){
    return request({
        url: `/eduorder/order/createOrder/${courseId}`,
        method: 'post'
      })
  },

  //根据订单id查询订单信息
  getOrdersInfo(orderId){
    return request({
        url: `/eduorder/order/getOrderInfo/${orderId}`,
        method: 'get'
      })
  },

  
  //生成二维码
  createNative(orderNo){
    return request({
        url: `/eduorder/pay-log/createNative/${orderNo}`,
        method: 'get'
      })
  },

  
  //查询订单状态
  queryPayStatus(orderNo){
    return request({
        url: `/eduorder/pay-log/queryPayStatus/${orderNo}`,
        method: 'get'
      })
  }
}