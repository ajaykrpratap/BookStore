const mysql = require('mysql');
const connection = mysql.createConnection({
    host: "*******.rds.amazonaws.com",
    database: "mydb",
    user: "o3_user",
    password: "******",
    port: "3306",
    debug: true

});

connection.connect(function(err) {
    if (err) {
      console.error('Database connection failed: ---------------');
      throw err;
    }
    console.log('Connected to database--------------------.');
  });
  module.exports = connection;
