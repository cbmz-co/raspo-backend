<template>
  <div id="app">
    <b-navbar toggleable="md" type="dark" variant="dark">

      <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>

      <b-navbar-brand to="/">Raspo</b-navbar-brand>

      <b-collapse is-nav id="nav_collapse">

        <b-navbar-nav>
          <b-nav-item to="create">New Device</b-nav-item>
          <b-nav-item to="offline">Offline</b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <b-row class="m-2"></b-row>
    <b-container>
      <router-view/>
      <b-row class="m-2"></b-row>
      <b-alert :show="dismissCountDown"
          dismissible
          variant="danger"
          @dismissed="dismissCountdown=0"
          @dismiss-count-down="countDownChanged">
        <h6 class="alert-heading">{{error ? error.message || '' : ''}}</h6>
        <hr>
        <p class="mb-0">
          {{error ? error.exception || '' : ''}}
        </p>
      </b-alert>
    </b-container>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'App',
  data () {
    return {
      dismissSecs: 10,
      dismissCountDown: 0
    }
  },
  computed: {
    ...mapGetters({error: 'getLastError'})
  },
  watch: {
    error: function () {
      this.showAlert()
    }
  },
  methods: {
    countDownChanged (dismissCountDown) {
      this.dismissCountDown = dismissCountDown
    },
    showAlert () {
      this.dismissCountDown = this.dismissSecs
    }
  }
}
</script>

<style>
#app {
  color: #2c3e50;
}
</style>
