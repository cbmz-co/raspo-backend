import * as moment from 'moment'

export const allDevices = state => {
  return state.devices
}

export const latestCreatedDevice = state => {
  return state.devices.filter((device) => device.createDate !== undefined &&
                                          device.createDate !== null &&
                                          moment(device.createDate) >= moment().subtract(5, 'minutes')
  )
}
export const devicesOffline = state => {
  return state.devices.filter((device) => moment(device.lastSignal) < moment().subtract(1, 'minutes'))
}

export const registeredDevices = state => {
  return state.devices.filter((device) => device.user !== undefined && device.user !== null)
}

export const unregisteredDevices = state => {
  return state.devices.filter((device) => device.user === undefined || device.user === null)
}

export const getDevice = state => {
  return state.device
}

export const getErrors = state => {
  return state.errors
}

export const getLastError = state => {
  return state.errors[state.errors.length - 1]
}
