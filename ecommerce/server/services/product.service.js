const sql = require('../shared/db');
const uuidv1 = require('uuid/v1')
module.exports = {
    create,
    getById,
    getByCat,
    getByName,
   
    getPaginatedList,
    update,
    remove,
    getList,
  
}
async function create(req,result){
    console.log(req.body);
    let product = {Id: uuidv1(),Name : req.body.Name,Code : req.body.Code,IsActive : 1,Description : req.body.Description,CategoryId : req.body.CategoryId,Color:req.body.Color,Size:req.body.Size,Quantity:req.body.Quantity,Image:req.body.Image,Price:req.body.Price}
    sql.query("INSERT INTO `products` SET ?", product, function (err, data) {             
        if(err) {
            result(err, null);
        }
        else{
            result(null,null);
        }
    });
}
async function getById(id,result) {
    sql.query("select * from products where id = ?  and IsActive=1", id, function (err, data) {             
        if(err) {
            result(err, null);
        }
        else{
            if(data.length < 1)  { 
                result(null,null);
            }
            else{   
                result(null,data[0]);           
            }
        }
    });
}
async function getByCat(id,result) {
    console.log(id);
    sql.query("select products.*,category.Id as cid,category.Name as CategoryId,order_items.id as oid,order_items.Productid as opd,IFNULL(sum(order_items.Quantity), 0) as OQuantity from products left join category on products.CategoryId=category.id left join order_items on products.id=order_items.Productid where products.CategoryId = ?  and products.IsActive=1 group by products.Id", id, function (err, data) {             
        if(err) {
            result(err, null);
        }
        else{
            if(data.length < 1)  { 
                result(null,null);
            }
            else{   
                result(null,data);           
            }
        }
    });
}


async function getByName(req,result) {
    if(req.body.name=="" || req.body.name==null){
        sql.query("select products.*,category.Id as cid,category.Name as CategoryId,order_items.id as oid,order_items.Productid as opd,IFNULL(sum(order_items.Quantity), 0) as OQuantity from products left join category on products.CategoryId=category.id left join order_items on products.id=order_items.Productid where products.CategoryId = ?  and IsActive=1 group by products.Id", [req.body.id], function (err, data) {             
            if(err) {
                result(err, null);
            }
            else{
                if(data.length < 1)  { 
                    result(null,null);
                }
                else{   
                    result(null,data);           
                }
            }
        });
    }else{
        sql.query(" select products.*,category.Id as cid,category.Name as CategoryId,order_items.id as oid,order_items.Productid as opd,IFNULL(sum(order_items.Quantity), 0) as OQuantity from products left join category on products.CategoryId=category.id left join order_items on products.id=order_items.Productid where products.CategoryId = ? and products.Name like  ?   and products.IsActive=1", [req.body.id,"%"+req.body.name+"%"], function (err, data) {
                  if(err) {
                      result(err, null);
                  }
                  else{
                      if(data.length < 1)  {
                          result(null,null);
                      }
                      else{
                          result(null,data);
                      }
                  }
              });
    }
   
}
async function remove(id,result) {
    sql.query("update products set IsActive=0 where id = ? ", id, function (err, data) {             
        if(err) {
            result(err, null);
        }
        else{
            result(null,null);
        }
    });
}
async function update(req,result) {
    sql.query("update products set Name=?,Code=?,Description=?,CategoryId=?,Color=?,Size=?,Quantity=?,Image=?,Price=? where Id = ? ", [req.body.Name,req.body.Code,req.body.Description,req.body.CategoryId,req.body.Color,req.body.Size,req.body.Quantity,req.body.Image,req.body.Price,req.body.Id], function (err, data) {             
        if(err) {
            result(err, null);
        }
        else{
            result(null,null);
        }
    });
}
async function getPaginatedList(req,result) {
    sql.query("select p.Id, p.Name,p.Code,p.Image,p.Price,c.Name as Category from products p inner join Category c on c.Id = p.CategoryId where IsActive=1 ", function (err, response) {             
        if(err) {
            result(err, null);
        }
        else{
            result(null,response);
        }
    });
}

async function getList(result) {
    sql.query("select Id, Name,CategoryId from products", function (err, response) {             
        if(err) {
            result(err, null);
        }
        else{
            result(null,response);
        }
    });
}
