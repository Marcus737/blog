import axios from '../utils/request.js'

// 新增留言
export const add = data => axios.post('/msg/addMsg', data)

// 获取留言列表
export const list =  (pageNum, pageSize) => axios.get('/msg/getMsgList', {params: {pageNum,pageSize}})