import axios from 'axios'

export async function getAllDevices () {
  try {
    const response = await axios.get('/api/device')
    return response.data
  } catch (error) {
    console.log(error)
    return null
  }
}

export async function checkRegisterDevice (id) {
  try {
    const response = await axios.get('/api/register/' + id)
    return response.data
  } catch (error) {
    console.log(error)
    return null
  }
}

export async function registerDevice (params) {
  try {
    const response = await axios.post('/api/register', params)
    return response.data
  } catch (error) {
    console.log(error)
    return null
  }
}
