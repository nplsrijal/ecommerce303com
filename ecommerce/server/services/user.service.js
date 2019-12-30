const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');
const sql = require('../shared/db');
const config = require('../shared/config');
const uuidv1 = require('uuid/v1');
const multer = require('multer');
const path =require('path');
const nodeMailer = require('nodemailer');
const moment = require('moment');

var storage = multer.diskStorage({
    destination: (req, file, cb) => {
      cb(null, './public/uploads/user')
    },
    filename: (req, file, cb) => {
      // console.log(file.originalname);
      // console.log(file.fieldname);
      let ext=path.extname(file.originalname);
      cb(null, file.fieldname + '-' + Date.now() + '.jpg')
    }
});
var upload = multer({storage: storage}).single('imageFile');
module.exports = {
    authenticate,
    create,
    getById,
    getPaginatedList,
    update,
    remove,
    uploadHandler,
    logout,contact,email,otp,reset
}
async function authenticate(req,result){
    console.log(req.body);
    sql.query("select * from user where EmailId = ? ", req.body.email, function (err, data) {             
        if(err) {
            result(err, null);
        }
        else{
            if(data.length < 1)  { 
                result('Incorrect Email or password.',null); 
            }
            else{   
                if(bcrypt.compareSync(req.body.Password, data[0].PasswordHash)) {
                let token = jwt.sign({UserId: data[0].Id, EmailId : data[0].EmailId, RoleId : data[0].RoleId},
                    config.secret,
                    { expiresIn: '24h' // expires in 24 hours
                    }
                );
                let usertype="";
                if(data[0].RoleId=='2998b1e357c840028236f2a930843af3'){
                     usertype="admin";
                }else{
                    usertype="user";
                }
                
                // return the JWT token for the future API calls
                result(null,{
                    status: 200,
                    success:true,
                    token: token,
                    fullname : data[0].FullName,
                    userid:data[0].Id,
                    usertype : usertype
                });
                }     
                else {
                    result(null,{
                        status: 400,
                        success:false,
                        message:"Incorrect Username or Password"
                       
                    });
                }                 
            }
        }
    });
}
async function create(req,result){
   // console.log(req.body);
    let user = {Id: uuidv1(),FullName : req.body.FullName,IsActive : 1, PasswordHash : bcrypt.hashSync(req.body.Password, 10),AccessFailedCount: 0,EmailId:req.body.EmailId,Phone:req.body.Phone,Location:req.body.Location,RoleId:req.body.RoleId}
    sql.query("INSERT INTO `user` SET ?", user, function (err, data) {             
        if(err) {
            result(err, null);
        }
        else{
            result(null,null);
        }
    });
}
async function getById(id,result) {
    sql.query("select Id,Fullname,EmailId,RoleId,Image,Phone,Location from user where id = ? ", id, function (err, data) {             
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
    sql.query("delete from user where id = ? ", id, function (err, data) {             
        if(err) {
            result(err, null);
        }
        else{
            result(null,null);
        }
    });
}
async function update(req,result) {
if(!req.body.image){
    sql.query("update user set FullName=?,EmailId=?,Phone=?,Location=? where Id = ? ", [req.body.FullName,req.body.EmailId,req.body.Phone,req.body.Location,req.body.id], function (err, data) {             
        if(err) {
            result(err, null);
        }
        else{
            result(null,null);
        }
    });
} else{
    sql.query("update user set FullName=?,EmailId=?,Phone=?,Location=?,Image=? where Id = ? ", [req.body.FullName,req.body.EmailId,req.body.Phone,req.body.Location,req.body.image,req.body.id], function (err, data) {             
        if(err) {
            result(err, null);
        }
        else{
            result(null,null);
        }
    });
}   

}
async function getPaginatedList(req,result) {
    sql.query("select  Id, FullName, EmailId, IsActive from user ", function (err, response) {             
        if(err) {
            result(err, null);
        }
        else{
            result(null,{
                data : response[0],
                count : response[1][0].total
            });
        }
    });
}
function uploadHandler(req,result){
    upload(req,result,function(err) {
        if(err) {
            return result(err,"Error uploading file.");
        }
        console.log(req.file);
        result(null,req.file);

      
    });

}
async function logout(req,result) {

   

}
async function contact(req,result){
    // console.log(req.body);
     let user = {Id: uuidv1(),Userid : req.body.Userid,IsSeen : 0, Subject:req.body.Subject,Message:req.body.Message}
     sql.query("INSERT INTO `user_contact` SET ?", user, function (err, data) {             
         if(err) {
             result(err, null);
         }
         else{
             result(null,null);
         }
     });
 }

 async function email(req,result){
    sql.query("select * from user where EmailId = ? ", req.body.email, function (err, data) {             
        if(err) {
            result(err, null);
        }
        else{
            if(data.length < 1)  { 
                result(null,{
                   val:0
                }
                    );            }
            else{  
                let currtime= moment().format(' YYYY-MM-DD hh:mm:ss');

                let finaltime= moment().add(1, 'minutes').format(' YYYY-MM-DD hh:mm:ss');
             
                  console.log(currtime);
                  var val = Math.floor(1000 + Math.random() * 9000);
                 console.log(val);
                 let transporter = nodeMailer.createTransport({
                     host: 'smtp.gmail.com',
                     port: 465,
                     secure: true,
                     auth: {
                         // should be replaced with real sender's account
                         user: 'srijal.fantastic@gmail.com',
                         pass: 'ctedodxbyhwmdnsr'
                     }
                 });
                 let mailOptions = {
                     // should be replaced with real recipient's account
                     to: req.body.email,
                     subject: "OTP PIN for Password Recovery",
                     html: "Your OTP Pin is "+val + ", IF you havenot made this decision. Ignore the message. Thank you."
                 };
                 transporter.sendMail(mailOptions, (error, info) => {
                     if (error) {
                         result(error, null);
                     }
                     console.log('Message %s sent: %s', info.messageId, info.response);
                     result(null,{
                         currtime:currtime,finaltime:finaltime,val:val
                     }
                         );
                 });
             }
   
 }
});
 }
 async function otp(req,result){
     console.log(req.body);
     let cDate = new Date(req.body.currentDateandTime);
     let initialDate = new Date(req.body.currtime);
     let finalDate = new Date(req.body.finaltime);
     let code=parseInt(req.body.code);
     let inputcode=parseInt(req.body.usercode);

     if(cDate >= initialDate && cDate <= finalDate){
        console.log("true");
        if(code==inputcode){
            console.log("success");
            result(null,{
                success:true
            });
        }else{
            result(null,{
                success:false
            });

        }
       // return true;
    }
    else{
        console.log("false");
        result(null,{
            success:false
        });

    }
   
 }
 async function reset(req,result) {
    sql.query("update user set PasswordHash=? where EmailId = ? ", [bcrypt.hashSync(req.body.password, 10),req.body.email], function (err, data) {             
        if(err) {
            result(err, null);
        }
        else{
            result(null,null);
        }
    });

 }
