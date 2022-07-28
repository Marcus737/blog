import axios from '../utils/request.js'

// 新增一篇文章
export const add = data => axios.post('/article/addArticle', data)

// 获取文章列表
export const list = (pageNum, pageSize) => axios.get('/article/getArticleList', {params: {pageNum,pageSize}})

// 获取文章详情
export const detail = (id) => axios.get('/article/getArticleDetail', { params: { id } })

// 喜欢这篇文章
export const setLike = (id, userId) => axios.get('/article/likeArticle', { params: { id, userId } })