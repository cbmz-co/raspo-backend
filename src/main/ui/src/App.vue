<template>
  <div id="app">
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
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
