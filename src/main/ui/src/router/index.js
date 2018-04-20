import Vue from 'vue'
import Router from 'vue-router'
import DevicesList from '@/components/DevicesList'
import RegisterDevice from '@/components/RegisterDevice'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'DeviceList',
      component: DevicesList
    },
    {
      path: '/register/:id',
      name: 'RegisterDevice',
      component: RegisterDevice,
      props: true
    }
  ]
})
