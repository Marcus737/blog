import axios from '../utils/request.js'

// 新增评论
export const add = data => axios.post('/articleWord/addArticleWord', data)

// 获取评论列表
export const list = (articleId, pageNum, pageSize) => axios.get('/articleWord/getArticleWordList', {params: {articleId, pageNum, pageSize}})