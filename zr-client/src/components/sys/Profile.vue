<template>
  <div>
    <el-card>
      <el-image :src="imageUrl" fit="contain">
        <div slot="error" class="image-slot">
          <i class="el-icon-picture-outline"></i>
        </div>
      </el-image>
      <div style="padding: 14px;">
        <div class="bottom clearfix">
          <el-upload ref="upload" :action="action" :headers="headers" :on-remove="handleRemove" :limit="1"
            accept="image/jpeg" :before-upload="beforeUpload" :auto-upload="false" :on-success="uploadSuccess"
            :on-error="uploadError">
            <el-button slot="trigger" size="small" type="text">选取图片</el-button>
            <el-button style="margin-left: 10px;" size="small" type="text" @click="submitUpload">上传图片</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过5MB</div>
          </el-upload>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        action: '/api/sys/user/uploadImg',
        headers: {
          Token: this.$store.state.token
        }
      }
    },
    methods: {
      beforeUpload(file) {
        let types = ['image/jpeg'];
        const isImage = types.includes(file.type);
        const isLtSize = file.size / 1024 / 1024 < 5;
        if (!isImage) {
          this.$message({
            showClose: true,
            message: '上传图片只能是JPG格式!',
            type: 'error'
          });
          return false;
        }
        if (!isLtSize) {
          this.$message.error('上传图片大小不能超过 5MB!');
          return false;
        }
        return true;
      },
      submitUpload() {
        this.$refs.upload.submit();
      },
      handleRemove(file) {
        this.$refs.upload.clearFiles();
      },
      uploadSuccess(response, file, fileList) {
        if (response.success) {
          this.$store.commit('headImgUrl', response.obj);
        }
        this.$message({
          type: response.success ? "success" : "error",
          message: response.msg
        })
      },
      uploadError(err, file, fileList) {
        this.$message({
          showClose: true,
          message: err,
          type: 'error'
        });
      }
    },
    computed: {
      imageUrl() {
        return 'data:image/jpeg;base64,' + this.$store.state.headImgUrl;
      }
    }
  }
</script>

<style>
  .el-card {
    width: 50%;
  }
</style>
