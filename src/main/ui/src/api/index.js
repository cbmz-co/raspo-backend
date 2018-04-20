import axios from 'axios'

export async function getAllDevices () {
  const response = await axios.get('/api/device')
  return response.data
}

export async function checkRegisterDevice (id) {
  const response = await axios.get('/api/register/' + id)
  return response.data
}

export async function registerDevice (params) {
  const response = await axios.post('/api/register', params)
  return response.data
}
