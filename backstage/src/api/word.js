import axios from '../utils/request.js'

// 留言
export const list =  (pageNum, pageSize) => axios.get('/msg/getMsgList', {params: {pageNum,pageSize}})
export const deleteWord = msgId => axios.get('/msg/delMsg',{params:{msgId}})

//评论
export const articleWordlist =  (pageNum, pageSize) => axios.get('/articleWord/getArtWordList', {params: {pageNum,pageSize}})
export const deleteArticleWord = id => axios.get('/articleWord/delArticleWord',{params:{id}})
