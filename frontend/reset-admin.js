const http = require('http');
const mysql = require('mysql2/promise');
const bcrypt = require('bcrypt');

function post(path, body) {
  return new Promise((resolve, reject) => {
    const data = new URLSearchParams(body).toString();
    const req = http.request({
      hostname: 'localhost', port: 8080, path, method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded', 'Content-Length': Buffer.byteLength(data) }
    }, res => { let c=''; res.on('data',x=>c+=x); res.on('end',()=>resolve(c)); });
    req.on('error', reject); req.write(data); req.end();
  });
}

(async () => {
  const conn = await mysql.createConnection({ host:'localhost', port:3306, user:'root', password:'123456', database:'mall' });
  const hash = await bcrypt.hash('admin123', 10);
  const [r] = await conn.query('UPDATE mall_user SET password = ? WHERE username = ?', [hash, 'admin']);
  console.log('DB updated rows:', r.affectedRows);
  await conn.end();
  console.log('LOGIN:', await post('/api/user/login', { username:'admin', password:'admin123' }));
})();