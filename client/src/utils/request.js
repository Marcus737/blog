// 发送请求的时候，如果有token，需要附带在请求头中
// 响应的时候，如果有token，保存到本地
// 响应的时候，如果响应的消息码是403( 没有token，token失效),在本地删除token
import axios from 'axios'
import { Message } from "element-ui"

const baseURL = "http://localhost:5362"

const ins = axios.create({
  baseURL,
  timeout: 15000,
  withCredentials: false
})
//发请求时携带token
ins.interceptors.request.use(req => {
  const token = localStorage.getItem("token")

  if (token != null && token.length > 0) {
    req.headers['authorization'] = "bearer " + token
  }
  const refreshToken = localStorage.getItem("refreshToken")

  if (refreshToken != null && refreshToken.length > 0) {
    req.headers['refreshToken'] = refreshToken
  }

  return req
},err => {
  return Promise.reject(err)
})

ins.interceptors.response.use(resp => {
  //设置token
  if (resp.headers["newToken"]) {
    localStorage.setItem("token", resp.headers["newToken"])
  }
  const data = resp.data
  if (data.code !== 200 ) {
    Message({
      type: 'error',
      message: data.msg,
      offset: 60
    })
    return Promise.reject(data)
  }
  return resp
}, err => {
  if (err.response.data.code === 401) {
    localStorage.removeItem('token')
    localStorage.removeItem('refreshToken')
    localStorage.removeItem('userInfo')

    Message({
      type: 'error',
      message: '未登录',
      offset: 60
    })
  }
  return Promise.reject(err)
})

export default ins
