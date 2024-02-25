import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/application',
      name: 'clientApplication',
      component: () => import('../views/Applications/Clients/View.vue'),
      meta: { authType: 'user' }
    },
      /* Teachers route */
    {
      path: '/',
      alias: '/teachers',
      name: 'teachers',
      component: () => import('../views/Teachers/View.vue'),
      meta: { authType: 'admin' }
    },
    {
      path: '/teachers/create',
      name: 'teacherCreate',
      component: () => import('../views/Teachers/Create.vue'),
      meta: { authType: 'admin' }
    },
    {
      path: '/teachers/:id/edit',
      name: 'teacherEdit',
      component: () => import('../views/Teachers/Edit.vue'),
      meta: { authType: 'admin' }
    },
      /* Courses route */
    {
      path: '/courses',
      name: 'courses',
      component: () => import('../views/Courses/View.vue'),
      meta: { authType: 'admin' }
    },
    {
      path: '/courses/create',
      name: 'coursesCreate',
      component: () => import('../views/Courses/Create.vue'),
      meta: { authType: 'admin' }
    },
    {
      path: '/courses/:id/edit',
      name: 'coursesEdit',
      component: () => import('../views/Courses/Edit.vue'),
      meta: { authType: 'admin' }
    },
      /* Groups route */
    {
      path: '/groups',
      name: 'groups',
      component: () => import('../views/Groups/View.vue'),
      meta: { authType: 'admin' }
    },
    {
      path: '/groups/create',
      name: 'groupsCreate',
      component: () => import('../views/Groups/Create.vue'),
      meta: { authType: 'admin' }
    },
    {
      path: '/groups/:id/edit',
      name: 'groupsEdit',
      component: () => import('../views/Groups/Edit.vue'),
      meta: { authType: 'admin' }
    },

      /* Clients route */
    {
      path: '/clients',
      name: 'clients',
      component: () => import('../views/Clients/View.vue'),
      meta: { authType: 'admin' }
    },
    {
      path: '/clients/create',
      name: 'clientsCreate',
      component: () => import('../views/Clients/Create.vue'),
      meta: { authType: 'admin' }
    },
    {
      path: '/clients/:id/edit',
      name: 'clientsEdit',
      component: () => import('../views/Clients/Edit.vue'),
      meta: { authType: 'admin' }
    },

    /* Clients route */
    {
      path: '/applications',
      name: 'applications',
      component: () => import('../views/Applications/Admin/View.vue'),
      meta: { authType: 'admin' }
    },
    {
      path: '/applications/:id/edit',
      name: 'applicationsEdit',
      component: () => import('../views/Applications/Admin/Edit.vue'),
      meta: { authType: 'admin' }
    },


    /* Users route */
    {
      path: '/users',
      name: 'users',
      component: () => import('../views/Users/View.vue'),
      meta: { authType: 'admin' }
    },
    {
      path: '/users/create',
      name: 'usersCreate',
      component: () => import('../views/Users/Create.vue'),
      meta: { authType: 'admin' }
    },
    {
      path: '/users/:id/edit',
      name: 'usersEdit',
      component: () => import('../views/Users/Edit.vue'),
      meta: { authType: 'admin' }
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/Auth/Register.vue'),
      meta: { authType: 'guest' }
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/Auth/Login.vue'),
      meta: { authType: 'guest' }
    }
  ]
})

router.beforeEach((to, from, next) => {
  const currentUser = JSON.parse(localStorage.getItem('user'))
  const requiredType = to.meta.authType

  if(!requiredType || currentUser === null && to.path !== '/login' && to.path !== '/register') {
    next('/login')
  }

  else if(requiredType === 'guest' || requiredType === currentUser.type )
    next()
})

export default router
