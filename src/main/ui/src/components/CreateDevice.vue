<template>
  <div id="create-device">
      <header>
          <h2>Create a device</h2>
      </header>
      <main>
          <b-form @submit="onSubmit">
              <b-form-group id="macAddressInputGroup"
                            label="Mac Address:"
                            label-for="macAddressInput">
                <b-form-input id="macAddressInput"
                v-model="form.mac"
                type="text"
                required
                placeholder="1a:3b:5c:7d:9e:2f">
                </b-form-input>
              </b-form-group>
              <b-button type="submit" variant="dark">Submit</b-button>
          </b-form>
          <b-row class="m-2">
            <b-table  striped
                    small
                    responsive
                    :items="latest"
                    :fields="fields">
              <caption>Latest inserted</caption>
            </b-table>
          </b-row>
      </main>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'CreateDevice',
  data () {
    return {
      form: {
        mac: ''
      },
      fields: [ 'id', 'mac' ]
    }
  },
  computed: {
    ...mapGetters({latest: 'latestCreatedDevice'})
  },
  methods: {
    onSubmit: async function (evt) {
      evt.preventDefault()
      await this.createDevice(this.form)
    },
    ...mapActions(['createDevice', 'getAllDevices'])
  },
  created: function () {
    this.getAllDevices()
    setInterval(function () {
      this.getAllDevices()
    }.bind(this), 30000)
  }
}
</script>

<style>

</style>
