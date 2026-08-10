// 仅用于本地调试，列出 mall_user 表里所有用户名和 id
const mysql = require('mysql2/promise');

(async () => {
  const conn = await mysql.createConnection({
    host:'localhost', port:3306, user:'root', password:'123456', database:'mall'
  });
  const [rows] = await conn.query('SELECT id, username, nickname, status, create_time FROM mall_user ORDER BY id');
  console.log(JSON.stringify(rows, null, 2));
  await conn.end();
})();