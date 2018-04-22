import * as api from '../api'
import * as moment from 'moment'

export const getAllDevices = async ({ dispatch, commit }) => {
  try {
    const devices = await api.getAllDevices()
    commit('receiveAll', devices)
    return 'success'
  } catch (e) {
    console.log(e)
    dispatch({
      type: 'newError',
      message: 'Receive all devices failed',
      e: e})
    return 'fail'
  }
}

export const checkRegisterDevice = async ({ dispatch, commit }, payload) => {
  try {
    const device = await api.checkRegisterDevice(payload)
    commit('receiveDevice', device)
    return 'success'
  } catch (e) {
    console.log(e)
    dispatch({
      type: 'newError',
      message: 'Check Device Registration failed, id: ' + payload,
      e: e})
    return 'fail'
  }
}

export const createDevice = async ({ dispatch }, payload) => {
  try {
    await api.createDevice(payload)
    dispatch('getAllDevices')
    return 'success'
  } catch (e) {
    console.log(e)
    dispatch({
      type: 'newError',
      message: 'Device creation failed, mac: ' + payload.mac,
      e: e})
    return 'fail'
  }
}

export const registerDevice = async ({ dispatch, commit }, payload) => {
  try {
    const device = await api.registerDevice(payload)
    commit('receiveDevice', device)
    return 'success'
  } catch (e) {
    console.log(e)
    dispatch({
      type: 'newError',
      message: 'Device Registration failed, id: ' + payload.id,
      e: e})
    return 'fail'
  }
}

export const resetDevice = ({ commit }) => {
  commit('resetDevice')
}
export const newError = ({ commit }, payload) => {
  const error = {
    exception: payload.e ? payload.e.message : null,
    time: moment(),
    message: payload.message
  }
  commit('addError', error)
}
