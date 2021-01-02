import axios from 'axios';

axios.defaults.headers.common['x-auth-token'] = window.localStorage.getItem("authToken");
axios.defaults.headers.common['Content-Type'] = 'application/json';

//SERVER ROUTES
export const USER_SERVER = '/api/v1/users';

