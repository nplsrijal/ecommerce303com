
const cartController = require("../controllers/cart.controller");



module.exports = function(app) {
  app.get('/api/cart/grid/list', cartController.getPaginatedList),
  app.post('/api/cart/create', cartController.create),
  app.post('/api/cart/update', cartController.update),
  app.delete('/api/cart/delete/:id', cartController.remove),
  app.delete('/api/cart/all/delete/:id', cartController.removeall),

  app.get('/api/cart/atcheckout/:id',cartController.getSum),

  app.get('/api/cart/Byuser/:id',cartController.getByUser)
}
