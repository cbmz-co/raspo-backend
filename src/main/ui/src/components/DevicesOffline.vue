<template>
  <div id="devices-offline">
    <header>
      <h2>Offline devices</h2>
    </header>
    <main>
      <template>
        <b-table  striped
                  small
                  responsive
                  :items="devices"
                  :fields="fields">
        </b-table>
      </template>
    </main>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'DevicesList',
  data () {
    return {
      fields: [ 'id', 'mac' ]
    }
  },
  computed: {
    ...mapGetters({devices: 'devicesOffline'})
  },
  methods: {
    ...mapActions(['getAllDevices'])
  },
  created: function () {
    this.getAllDevices()
    setInterval(function () {
      this.getAllDevices()
    }.bind(this), 15000)
  }
}
</script>

<style>

</style>
