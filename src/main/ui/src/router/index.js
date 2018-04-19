import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import DevicesList from '@/components/DevicesList'
import RegisterDevice from '@/components/RegisterDevice'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/devices',
      name: 'DevicesList',
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
