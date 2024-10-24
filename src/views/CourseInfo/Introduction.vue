<template>
  <div class="Intro">
    <p class="content">{{ courseIntro }}</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      courseIntro: '', // 用于存储课程介绍的变量
    };
  },
  mounted() {
    // 获取传递过来的课程介绍
    this.courseIntro = this.$route.query.courseIntro || '暂无课程介绍';
  },
};
</script>

<style>
.Intro {
  border-radius: 20px;
  box-shadow: --el-box-shadow-light;
  background-color: #FFFFFF;
  height: 580px;
  border: groove;
}

.content {
  font-family: 'Helvetica', 'Arial', sans-serif;
  /* 使用现代无衬线字体 */
  font-size: 18px;
  /* 设置适中的字体大小 */
  color: #333333;
  /* 文字颜色为深灰色，易于阅读 */
  line-height: 1.6;
  /* 增加行高，提高可读性 */
  text-align: justify;
  /* 使文字两端对齐，排版整齐 */
  padding: 20px;
  /* 添加内边距，避免文字紧贴边框 */
  letter-spacing: 0.5px;
  /* 增加字母间距，使文本更加舒适 */
  word-wrap: break-word;
  /* 允许长单词换行，避免溢出 */
  background-color: #f9f9f9;
  /* 背景颜色略微调亮，增加层次感 */
  border-radius: 10px;
  /* 增加圆角，保持一致性 */
}
</style>