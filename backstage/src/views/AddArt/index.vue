<template>
  <div class="content">
    <div class="title flex align-center">
      <div class="flex align-center"><span></span>无所谓好或不好，人生一场虚空大梦，韶华白首，不过转瞬。惟有天道恒在，往复循环，不曾更改！</div>
    </div>
    <div class="submit flex align-center">
      <el-cascader
          @change="handleChange"
          placeholder="搜索文章"
          :props="props"
          filterable></el-cascader>
    </div>
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="ruleForm">
      <el-form-item prop="title">
        <el-input v-model.trim="ruleForm.title" placeholder="标题"></el-input>
      </el-form-item>
      <el-form-item prop="description">
        <el-input v-model.trim="ruleForm.description" placeholder="描述"></el-input>
      </el-form-item>
      <el-form-item prop="musicName">
        <el-input v-model.trim="ruleForm.musicName" placeholder="音乐名称"></el-input>
      </el-form-item>
    </el-form>
    <div class="imgs flex align-center">
      <el-upload
        class="upload"
        drag
        :action="mainUrl + '/upload'"
        :data="{'pw': 'lunyu'}"
        :on-error="uploadError"
        accept="image/*"
        :on-success="handleAvatarSuccess"
        :show-file-list="false"
        :before-upload="beforeAvatarUpload"
        >
        <img v-if="imgUrl" :src="imgUrl" class="avatar">
        <i class="iconfont icon-jurassic_image" v-if="!imgUrl"></i>
        <div class="el-upload__text" v-if="!imgUrl">封面图片 (680*440)</div>
      </el-upload>
      <el-upload
        class="upload"
        drag
        :show-file-list="false"
        :data="{'pw': 'lunyu'}"
        accept="audio/*"
        :on-error="uploadError"
        :action="mainUrl + '/upload'"
        :on-success="handleMusicSuccess"
        :before-upload="beforeMusicUpload"
        >
        <i class="iconfont icon-Music1"></i>
        <div class="el-upload__text">{{musicText}}</div>
      </el-upload>
    </div>
    <Markdown :defaultContent = "this.ruleForm.content" @contentChange="contentChange" style="height: 400px" />
    <div class="submit flex align-center" >
      <el-button class="subBtn" type="primary" icon="el-icon-position" @click="addArticle">发布</el-button>
      <el-button class="subBtn" type="primary" icon="el-icon-delete" @click="delArt">删除</el-button>
      <el-button class="subBtn" type="primary" icon="el-icon-edit" @click="updateArt">修改</el-button>
    </div>

  </div>
</template>

<script>
import { add, detail, list,updateArticle, delArticle } from '@/api/article'
import { mapState } from 'vuex'
import Markdown from '@c/markdown'
export default {
  data () {
    return {
      props: {
        lazy: true,
        lazyLoad (node, resolve) {
          setTimeout(() => {
            list(1, 999).then(res => {
              res = res.data.data.datas;
              const nodes = res.map(item => ({
                    value: item.id,
                    label: item.title,
                    leaf: "disable"
                  }));
              // 通过调用resolve将子节点数据返回，通知组件数据加载完成
              resolve(nodes);
            });
          }, 1000);
        }
      },
      ruleForm: {
        id: '',
        title: '',
        description: '',
        musicName: '',
        content: ''
      },
      rules: {
        title: [
          { required: true, message: '请输入标题', trigger: 'blur' },
          { min: 0, max: 20, message: '不能超过20个字', trigger: 'blur' }
        ]
      },
      imgUrl: '',
      musicUrl: '',
      musicText: '背景音乐',
    }
  },
  components: { Markdown },
  computed: { ...mapState(['userInfo']) },
  methods: {
    handleChange(val){
      detail("" + val).then(res => {
        res = res.data.data;
        this.imgUrl = res.imgUrl;
        this.ruleForm.id = res.id;
        this.ruleForm.title = res.title;
        this.ruleForm.description = res.description;
        this.ruleForm.musicName = res.musicName;
        this.ruleForm.content = res.content;
      })
    },
    updateArt(){
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          if (!this.imgUrl) { this.$message.error('封面图片不能为空'); return }
          if (!this.ruleForm.content) { this.$message.error('文章内容不能为空'); return }
          if (!this.ruleForm.id) { this.$message.error('文章id不能为空'); return }
          const data = {
            ...this.ruleForm,
            imgUrl: this.imgUrl,
            musicUrl: this.musicUrl,
            content: this.ruleForm.content
          }
          updateArticle(data).then(res => {
            this.$message.success('更新文章成功')
          })
        }
      })
    },
    delArt(){
      if (!this.ruleForm.id) {
        this.$message.error('文章id不能为空');
        return;
      }

      this.$confirm('此操作将永久删除该文章, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delArticle(this.ruleForm.id).then(res => {
          this.$message.success('删除文章成功')

        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });


    },
    uploadUrl(){

    },
    beforeAvatarUpload(file) {
      const isLt4M = file.size / 1024 / 1024 < 4;
      if (!isLt4M) {
        this.$message.error('上传封面图片大小不能超过 4MB!');
      }
      return isLt4M;
    },
    handleAvatarSuccess(res, file) {
      this.imgUrl = res.data
      console.log(res)
      this.$message.success('图片上传成功')
    },
    beforeMusicUpload (file) {
      const isLt4M = file.size / 1024 / 1024 < 4;
      if (!isLt4M) {
        this.$message.error('上传音频大小不能超过4MB!');
      }
      isLt4M && (this.musicText = file.name)
      return isLt4M
    },
    handleMusicSuccess (res, file) {
      this.musicUrl = res.data
      this.$message.success('音频上传成功')
    },
    contentChange (e) {
      this.ruleForm.content = e
    },
    addArticle () {
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          if (!this.imgUrl) { this.$message.error('封面图片不能为空'); return }
          if (!this.ruleForm.content) { this.$message.error('文章内容不能为空'); return }
          const data = {
            ...this.ruleForm,
            imgUrl: this.imgUrl,
            musicUrl: this.musicUrl,
            content: this.ruleForm.content
          }
          add(data).then(res => {
            this.$message.success('添加文章成功')
          })
        }
      })
    },
    uploadError (e) {
      this.$message.error(JSON.parse(e.message).msg)
    }
  }
}
</script>

<style lang="scss" scoped>

  .ruleForm {
    margin-top: 40px;
  }

  .upload {
    &:first-of-type {
      margin-right: 20px;
    }
    &:hover {
      /deep/ .el-upload-dragger {
        border-color: #409EFF;
        .iconfont, .el-upload__text {
          color: #409EFF;
        }
      }
    }
    .avatar {
      width: 100%;
      height: 100%;
    }
    .iconfont {
      color: #a2a3a5;
      font-size: 34px;
      margin-bottom: 10px;
      transition: all .3s;
    }
    /deep/ .el-upload-dragger {
      width: 400px;
      height: 200px;
      transition: all .3s;
      border-color: #DCDFE6;
      display: flex;
      align-items: center;
      justify-content: center;
      flex-direction: column;
      .el-upload__text {
        color: #a2a3a5;
        transition: all .3s;
      }
      
    }
  }

  .submit {
    justify-content: flex-start;
    margin-top: 20px;
  }
</style>