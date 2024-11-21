
//对外暴露常量路由
export const constantRoute = [
  {
    //登录
    path: "/",
    component: () => import("@/views/login/index.vue"),
    name: "login", //命名路由
    meta: {
      title: '登录',
      hidden: true,//是否在菜单隐藏
      icon: 'Aim'
    }
  },
  {
    path: '/firstpage',
    component: () => import('@/views/firstpage/firstpage.vue'),
    name: 'firstpage',
    meta: {
      title: '首页面',
      hidden: true,//是否在菜单隐藏
      icon: 'CircleCloseFilled',
      key: 'unique-key'
    },
  },
  {
    path: '/bookmarks',
    component: () => import('@/views/Bookmarks/bookmarks.vue'),
    name: 'bookmarks',
    meta: {
      title: '收藏夹',
      hidden: true,//是否在菜单隐藏
      icon: 'CircleCloseFilled',
      key: 'unique-key'
    },
  },
  {
    path: '/bemarkedposts',
    component: () => import('@/views/Bookmarks/bemarkedposts.vue'),
    name: 'bemarkedposts',
    meta: {
      title: '收藏帖子',
      hidden: true,//是否在菜单隐藏
      icon: 'CircleCloseFilled',
      key: 'unique-key'
    },
  },
  {
    path: '/notes',
    component: () => import('@/views/Bookmarks/notes.vue'),
    name: 'notes',
    meta: {
      title: '笔记',
      hidden: true,//是否在菜单隐藏
      icon: 'CircleCloseFilled',
      key: 'unique-key'
    },
  },
  {
    path: '/othersnotes',
    component: () => import('@/views/Bookmarks/othersnotes.vue'),
    name: 'othersnotes',
    meta: {
      title: '所有笔记',
      hidden: true,//是否在菜单隐藏
      icon: 'CircleCloseFilled',
      key: 'unique-key'
    },
  },
  {
    path: '/postsDetail',
    component: () => import('@/views/Bookmarks/postsDetail.vue'),
    name: 'postsDetail',
    meta: {
      title: '帖子详情',
      hidden: true,//是否在菜单隐藏
      icon: 'CircleCloseFilled',
      key: 'unique-key'
    },
  },
  {
    path: '/myinformation',
    component: () => import('@/views/firstpage/myinformation.vue'),
    name: 'myinformation',
    meta: {
      title: '个人信息',
      hidden: true,
      icon: 'FolderAdd',
      key: 'unique-key'
    }

  },
  {
    path: '/changepwd',
    component: () => import('@/views/firstpage/changepwd.vue'),
    name: 'changepwd',
    meta: {
      title: '修改密码',
      hidden: true,
      icon: 'FolderAdd',
      key: 'unique-key'
    }

  },
  {
    path: '/CourseInfo',
    component: () => import("@/layout/index.vue"),
    name: 'CourseInfo',
    redirect: '/CourseInfo/introduction',
    meta: {
      title: '课程信息',
      hidden: false,
      icon: 'Folder',
    },
    children: [
      {
        path: '/CourseInfo/Introduction',
        component: () => import('@/views/CourseInfo/Introduction.vue'),
        name: 'introduction',
        meta: {
          title: '课程介绍',
          hidden: false,
          icon: 'Document'
        }
      },
      {
        path: '/CourseInfo/Syllabus',
        component: () => import('@/views/CourseInfo/Syllabus.vue'),
        name: 'Syllabus',
        meta: {
          title: '教学大纲',
          hidden: false,
          icon: 'Document'
        }
      },
      {
        path: '/CourseInfo/Calendar',
        component: () => import('@/views/CourseInfo/Calendar.vue'),
        name: 'Calendar',
        meta: {
          title: '教学日历',
          hidden: false,
          icon: 'calendar'
        }
      },
      {
        path: '/CourseInfo/TeacherInfo',
        component: () => import('@/views/CourseInfo/TeacherInfo.vue'),
        name: 'TeacherInfo',
        meta: {
          title: '教师信息',
          hidden: false,
          icon: 'Reading'
        }
      },
      {
        path: '/CourseInfo/Notify',
        component: () => import('@/views/CourseInfo/Notify.vue'),
        name: 'Notifies',
        meta: {
          title: '课程通知',
          hidden: false,
          icon: 'Notification'
        }
      },
    ]
  },
  {
    path: '/CourseResource',
    component: () => import("@/layout/index.vue"),
    name: 'CourseResource',
    redirect: '/CourseResource/File',
    meta: {
      title: '课程资源',
      hidden: false,
      icon: 'Folder',
    },
    children: [
      {
        path: '/CourseResource/File',
        component: () => import('@/views/CourseResource/File.vue'),
        name: 'File',
        meta: {
          title: '电子课件',
          hidden: false,
          icon: 'Files',
        }
      }
    ]
  },
  {
    path: '/discussion',
    component: () => import("@/layout/index.vue"),
    name: 'discussion',
    meta: {
      title: '讨论',
      hidden: false,//是否在菜单隐藏
      icon: 'Notification',
    }, children: [
      {
        path: '/discussion/discussionArea',
        component: () => import('@/views/discussion/discussionArea.vue'),
        name: 'discussionArea',
        meta: {
          title: '讨论区',
          hidden: false,
          icon: 'Files',
        }
      },
      {
        path: '/discussion/discussionInfo',
        component: () => import('@/views/discussion/discussionInfo.vue'),
        name: 'discussionInfo',
        meta: {
          title: '讨论详情',
          hidden: false,
          icon: 'Files',
        }
      },
      {
        path: '/discussion/createDiscussion',
        component: () => import('@/views/discussion/createDiscussion.vue'),
        name: 'createDiscussion',
        meta: {
          title: '新建帖子',
          hidden: true,
          icon: 'Files',
        }
      }
    ]
  },
  {
    path: '/CourseManagement',
    component: () => import("@/layout/index.vue"),
    name: 'CourseManagement',
    redirect: '/CourseManagement/Students',
    meta: {
      title: '课程管理',
      hidden: false,
      icon: 'Setting',
    },
    children: [
      {
        path: '/CourseManagement/Student',
        component: () => import('@/views/CourseManagement/Student.vue'),
        name: 'Students',
        meta: {
          title: '选课学生名单',
          hidden: false,
          icon: 'User',
        }
      },
      {
        path: '/CourseManagement/Notify',
        component: () => import('@/views/CourseManagement/Notify.vue'),
        name: 'Notify',
        meta: {
          title: '通知发布',
          hidden: false,
          icon: 'Notification',
        }
      },
      {
        path: '/CourseManagement/sendHomework',
        component: () => import('@/views/CourseManagement/sendHomework.vue'),
        name: 'sendHomework',
        meta: {
          title: '作业布置',
          hidden: false,
          icon: 'Reading',
        }
      },
      {
        path: '/CourseManagement/checkHomework',
        component: () => import('@/views/CourseManagement/checkHomework.vue'),
        name: 'checkHomework',
        meta: {
          title: '批改作业',
          hidden: false,
          icon: 'Reading',
        }
      },
      {
        path: '/CourseManagement/editHomework',
        component: () => import('@/views/CourseManagement/editHomework.vue'),
        name: 'editHomework',
        meta: {
          title: '编辑作业信息',
          hidden: true,
          icon: 'Reading',
        }
      },
      {
        path: '/CourseManagement/addHomework',
        component: () => import('@/views/CourseManagement/addHomework.vue'),
        name: 'addHomework',
        meta: {
          title: '新建作业',
          hidden: true,
          icon: 'Reading',
        }
      },
      {
        path: '/CourseManagement/accessoryPreview',
        component: () => import('@/views/CourseManagement/accessoryPreview.vue'),
        name: 'accessoryPreview',
        meta: {
          title: '附件预览',
          hidden: true,
          icon: 'Files',
        }
      },
      {
        path: '/CourseManagement/getHomeworkFinalGrade',
        component: () => import('@/views/CourseManagement/getHomeworkFinalGrade.vue'),
        name: 'getHomeworkFinalGrade',
        meta: {
          title: '作业最终成绩',
          hidden: true,
          icon: 'Files',
        }
      }
    ]
  },
  {
    path: '/homework',
    component: () => import("@/layout/index.vue"),
    name: 'homework',
    redirect: '/homework/homeworkList',
    meta: {
      title: '作业',
      hidden: false,
      icon: 'Folder',
    },
    children: [
      {
        path: '/homework/homeworkList',
        component: () => import('@/views/homework/homeworkList.vue'),
        name: 'homeworkList',
        meta: {
          title: '作业列表',
          hidden: false,
          icon: 'Files',
        }
      },
      {
        path: '/homework/homeworkInfo',
        component: () => import('@/views/homework/homeworkInfo.vue'),
        name: 'homeworkInfo',
        meta: {
          title: '作业详情',
          hidden: false,
          icon: 'Files',
        }
      },
      {
        path: '/homework/homeworkPreview',
        component: () => import('@/views/homework/homeworkPreview.vue'),
        name: 'homeworkPreview',
        meta: {
          title: '作业预览',
          hidden: true,
          icon: 'Files',
        }
      },
      {
        path: '/homework/homeworkCheckbystu',
        component: () => import('@/views/homework/homeworkCheckbystu.vue'),
        name: 'homeworkCheckbystu',
        meta: {
          title: '作业互评',
          hidden: false,
          icon: 'Files',
        }
      }
    ]
  },
  {
    path: '/AI',
    component: () => import("@/layout/index.vue"),
    name: 'AI',
    redirect: '/AI/AIhelper',
    meta: {
      title: 'AI',
      hidden: false,
      icon: 'Folder',
    },
    children: [
      {
        path: '/AI/AIhelper',
        component: () => import('@/views/AI/AIhelper.vue'),
        name: 'AIhelper',
        meta: {
          title: 'AI助手',
          hidden: false,
          icon: 'Files',
        }
      }
    ]
  },

  {
    //404
    path: "/404",
    component: () => import("@/views/404/index.vue"),
    name: "404",
    meta: {
      title: '404',
      hidden: true,
      icon: "CircleCloseFilled"
    }
  },
  {
    path: "/:pathMatch(.*)*",
    redirect: "/404",
    name: "any",
    meta: {
      title: '任意路由',
      hidden: true,
      icon: "CircleCloseFilled"
    }
  },

]