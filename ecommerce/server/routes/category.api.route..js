
const categoryController = require("../controllers/category.controller");



module.exports = function(app) {
  app.get('/api/setup/category/grid/list', categoryController.getPaginatedList),
  app.post('/api/setup/category/create', categoryController.create),
  app.post('/api/setup/category/update', categoryController.update),
  app.get('/api/setup/category/get/:id', categoryController.getById)
  app.get('/api/setup/category/delete/:id', categoryController.remove)
}
