import axios from '../utils/request.js'
import uploadFile from '../utils/uploadFile'

// 登录
export const login = (username, password) => axios.post('/user/login',{username, password})

// 获取我的个人信息
export const whoami = () => axios.get('/user/whoami')

// 修改用户信息
export const updateUserInfo = data => axios.post('/user/updateUserInfo', data)

// 获取首页信息
export const getHomeInfo = () => axios.get('/user/getHomeInfo')

// 上传图片
export const uploadImg = data => uploadFile.post('/upload', data)