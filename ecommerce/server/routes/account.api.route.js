
const userController = require("../controllers/user.controller");


module.exports = function(app) {
  app.post('/api/account/login',  userController.authenticate);
  app.post('/api/account/signup',  userController.create);
  app.post('/api/account/password', userController.email)
  app.post('/api/account/otpvalidate', userController.otp)
  app.post('/api/account/resetpassword', userController.reset)


}
