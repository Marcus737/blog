import axios from '../utils/request.js'

// 新增一篇文章
export const add = data => axios.post('/article/addArticle', data)

// 获取文章列表
export const list = (pageNum, pageSize) => axios.get('/article/getArticleList', {params: {pageNum,pageSize}})

// 获取文章详情
export const detail = (id) => axios.get('/article/getArticleDetail', { params: { id } })

//更新文章
export const updateArticle = data => axios.post('/article/updateArticle', data)

//删除文章
export const delArticle = (id) => axios.get('/article/delArticle', { params: { id } })
