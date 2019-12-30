
const userController = require("../controllers/user.controller");

module.exports = function(app) {
  app.get('/api/setup/user/grid/list', userController.getPaginatedList),
  app.post('/api/setup/user/create', userController.create),
  app.post('/api/setup/user/update', userController.update),
  app.post('/api/setup/user/upload', userController.upload),

  app.get('/api/setup/user/get/:id', userController.getById)
  app.get('/api/setup/user/delete/:id', userController.remove)
  app.get('/api/user/logout', userController.logout)
  app.post('/api/user/contact', userController.contact)


}
