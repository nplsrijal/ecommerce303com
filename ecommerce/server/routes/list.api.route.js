const roleController = require("../controllers/role.controller");
const categoryController = require("../controllers/category.controller");
const productController = require("../controllers/product.controller");



module.exports = function(app) {
  app.get('/api/list/role', roleController.getList),
  app.get('/api/list/category', categoryController.getList),
  app.get('/api/list/product', productController.getList)
  

}
