<template>
  <div id="devices">
    <header>
      <h2>All devices</h2>
    </header>
    <main>
      <template>
        <b-table  striped
                  small
                  responsive
                  :items="devices"
                  :fields="fields">
          <template slot="show_details" slot-scope="row">
            <!-- we use @click.stop here to prevent emitting of a 'row-clicked' event  -->
            <b-button size="sm" @click.stop="row.toggleDetails" class="mr-2" variant="secondary">
            {{ row.detailsShowing ? 'Hide' : 'Show'}} Details
            </b-button>
          </template>
          <template slot="row-details" slot-scope="row">
            <b-card>
              <b-row class="mb-2">
                <b-col sm="6">
                  <b-row class="d-flex justify-content-center">
                    <qrcode :value="'http://localhost:8080/#/register/' + row.item.id"  :options="{ size: 200 }"></qrcode>
                  </b-row>
                </b-col>
                <b-col sm="6">
                  <p>Username: {{row.item.user? row.item.user.username || 'undefined' : 'undefined'}}</p>
                  <p>Email: {{row.item.user? row.item.user.email || 'undefined' : 'undefined'}}</p>
                  <p>Last signal: {{row.item.lastSignal || 'undefined'}}</p>
                  <b-link :href="'http://localhost:8080/#/register/' + row.item.id">{{'#/register/' + row.item.id }}</b-link>
                </b-col>
              </b-row>
            </b-card>
          </template>
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
      fields: [ 'id', 'mac', 'show_details' ]
    }
  },
  computed: {
    ...mapGetters({devices: 'allDevices'})
  },
  methods: {
    ...mapActions(['getAllDevices'])
  },
  created: function () {
    this.getAllDevices()
  }
}
</script>

<style>

</style>
