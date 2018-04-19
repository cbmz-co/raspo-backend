<template>
    <div id="register-device">
        <header>
            <h2>Register a device</h2>
        </header>
        <main>
            <b-form>
                <b-form-input required v-model="form.username"
                  type="text"
                  placeholder="Enter your username"></b-form-input>
                <b-form-input required v-model="form.email"
                  type="text"
                  placeholder="Enter your email"></b-form-input>
                <b-form-input required disabled v-model="deviceId"
                  type="text"></b-form-input>
            </b-form>
        </main>
    </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'RegisterDevice',
  data () {
    return {
      form: {
        username: '',
        email: '',
        id: ''
      }
    }
  },
  props: ['id'],
  computed: {
    device () {
      return this.$store.getters.getDevice
    },
    deviceId: {
      get () {
        return this.$store.getters.getDevice ? this.$store.getters.getDevice.id : 'undefined'
      },
      set (value) {
        this.form.id = value
      }
    }
  },
  methods: mapActions(['checkRegisterDevice', 'registerDevice']),
  created: async function () {
    this.$store.dispatch('checkRegisterDevice', this.id)
  }
}
</script>

<style>

</style>
