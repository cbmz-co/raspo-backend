import * as api from '../api'

export const getAllDevices = async ({ commit }) => {
  const devices = await api.getAllDevices()
  commit('receiveAll', devices)
}

export const checkRegisterDevice = async ({ commit }, payload) => {
  const device = await api.checkRegisterDevice(payload)
  commit('receiveDevice', device)
}

export const registerDevice = async ({ dispatch }, payload) => {
  await api.registerDevice(payload)
  await dispatch('getAllDevices')
}
