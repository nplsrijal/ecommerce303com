
const orderController = require("../controllers/order.controller");



module.exports = function(app) {
  app.get('/api/order/grid/list', orderController.getPaginatedList),
  app.post('/api/order/create', orderController.create),
  app.post('/api/order/changestatus', orderController.updatestatus),

  app.get('/api/order/get/:id', orderController.getById)
  app.get('/api/order/getByUser/:id', orderController.getByUser)
}
