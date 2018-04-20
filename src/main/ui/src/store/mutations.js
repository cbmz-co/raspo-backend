import Vue from 'vue'

export default {
  receiveAll (state, devices) {
    devices.forEach((device, key) => {
      Vue.set(state.devices, key, device)
    })
  },
  receiveDevice (state, device) {
    state.device = device
  },
  resetDevice (state, device) {
    state.device = null
  },
  addError (state, error) {
    state.errors.push(error)
  }
}
