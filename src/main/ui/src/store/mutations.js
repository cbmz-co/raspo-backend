import Vue from 'vue'

export default {
  receiveAll (state, devices) {
    devices.forEach((device, key) => {
      Vue.set(state.devices, key, device)
    })
  },
  receiveDevice (state, device) {
    state.device = device
  }
}
