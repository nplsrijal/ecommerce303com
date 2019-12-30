
const productController = require("../controllers/product.controller");


module.exports = function(app) {
  app.get('/api/setup/product/grid/list', productController.getPaginatedList),
  app.post('/api/setup/product/create', productController.create),
  app.post('/api/setup/product/update', productController.update),
  app.get('/api/setup/product/get/:id', productController.getById),
  app.get('/api/setup/product/getByCat/:id', productController.getByCat),
  app.post('/api/setup/product/getByName', productController.getByName),

  app.get('/api/setup/product/delete/:id', productController.remove)
}
