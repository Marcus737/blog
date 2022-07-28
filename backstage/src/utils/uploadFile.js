import axios from 'axios'
import {Message} from "element-ui"

let baseURL = 'http://localhost:2689'

const ins = axios.create({
  baseURL,
  withCredentials: false
})

ins.interceptors.request.use(req => {
  // const token = localStorage.getItem("pw")
  //
  // if (token) {
  // }
  // req.data.pw="lunyu"
  req.params = {"pw": "lunyu"}
  console.log(req.params)
  return req
},err => {
  return Promise.reject(err)
})


ins.interceptors.response.use(resp => {
  const data = resp.data
  if (data.code !== 200 ) {
    Message.error(data.msg)
    return Promise.reject(data)
  }
  return resp
}, err => {
  return Promise.reject(err)
})

export default ins
