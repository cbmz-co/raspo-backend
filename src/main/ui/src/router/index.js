import Vue from 'vue'
import Router from 'vue-router'
import DevicesList from '@/components/DevicesList'
import RegisterDevice from '@/components/RegisterDevice'
import CreateDevice from '@/components/CreateDevice'
import DevicesOffline from '@/components/DevicesOffline'

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
    },
    {
      path: '/create',
      name: 'CreateDevice',
      component: CreateDevice
    },
    {
      path: '/offline',
      name: 'DevicesOffline',
      component: DevicesOffline
    }
  ]
})
