const sql = require('../shared/db');
const uuidv1 = require('uuid/v1')
module.exports = {
    create,
    getById,
    getPaginatedList,
    update,
    remove,
    getList,
    getCount
}
async function create(req,result){
    let category = {Id: uuidv1(),Name : req.body.Name}
    sql.query("INSERT INTO `category` SET ?", category, function (err, data) {             
        if(err) {
            result(err, null);
        }
        else{
            result(null,null);
        }
    });
}
async function getById(id,result) {
    sql.query("select Id,Name from category where id = ? ", id, function (err, data) {             
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
async function remove(id,result) {
    sql.query("delete from category where id = ? ", id, function (err, data) {             
        if(err) {
            result(err, null);
        }
        else{
            result(null,null);
        }
    });
}
async function update(req,result) {
    sql.query("update category set Name=? where Id = ? ", [req.body.Name,req.body.Id], function (err, data) {             
        if(err) {
            result(err, null);
        }
        else{
            result(null,null);
        }
    });
}
async function getPaginatedList(req,result) {
    sql.query("select  Id, Name , Image from category", function (err, response) {             
        if(err) {
            result(err, null);
        }
        else{
            console.log(response);
            result(err,response);
        }
    });
}

async function getList(result) {
    sql.query("select Id, Name from category", function (err, response) {             
        if(err) {
            result(err, null);
        }
        else{
            result(null,response);
        }
    });
}
async function getCount(result) {
    sql.query("select count(id) as totalcount from category", function (err, response) {             
        if(err) {
            result(err, null);
        }
        else{
            result(null,response);
        }
    });
}

