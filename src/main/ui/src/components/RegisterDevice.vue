<template>
    <div id="register-device">
        <header>
            <h2>Register a device</h2>
        </header>
        <main>
          <b-form @submit="onSubmit">
            <b-form-group id="usernameInputGroup"
                          label="Username:"
                          label-for="usernameInput">
              <b-form-input id="usernameInput"
                            v-model="form.username"
                            type="text"
                            required
                            placeholder="Enter your username">
              </b-form-input>
            </b-form-group>
            <b-form-group id="emailInputGroup"
                          label="Email:"
                          label-for="emailInput">
              <b-form-input id="emailInput"
                            v-model="form.email"
                            type="text"
                            required
                            placeholder="Enter your email">
              </b-form-input>
            </b-form-group>
            <b-form-group id="deviceIdInputGroup"
                          label="Device:"
                          label-for="deviceIdInput">
              <b-form-input id="deviceIdInput"
                          :value="device ? device.id : 'undefined'"
                          type="text"
                          required
                          disabled>
              </b-form-input>
            </b-form-group>
            <b-button type="submit" variant="dark">Submit</b-button>
          </b-form>
        </main>
    </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

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
    ...mapGetters(
      {
        device: 'getDevice'
      }
    )
  },
  methods: {
    onSubmit: async function (evt) {
      this.form.id = this.device ? this.device.id : null
      evt.preventDefault()
      await this.registerDevice(this.form)
    },
    ...mapActions(['checkRegisterDevice', 'registerDevice', 'resetDevice'])
  },
  created: async function () {
    this.resetDevice()
    this.checkRegisterDevice(this.id)
  }
}
</script>

<style>

</style>
