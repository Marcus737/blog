import axios from '../utils/request'

// 登陆
export const login = (username, password) => axios.post('/user/login',{username, password})

// 增加用户
export const addUser = (data) => axios.post('/user/addUser', data)

// 注册 按我的博客逻辑是需要验证码的修改用户信息。。。
export const registry = (data) => axios.post('/user/createUser', data)

// 修改用户信息
export const updateUserInfo = data => axios.post('/user/updateUserInfo', data)

// 获取个人信息
export const whoami = () => axios.get('/user/whoami')

// 获取管理员的个人信息，必须在数据库里面id为 **1**
export const getAboutMe = () => axios.get('/user/getAboutMe')