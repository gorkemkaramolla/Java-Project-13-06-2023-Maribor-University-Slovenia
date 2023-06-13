var http = require('http');
var fs = require('fs');
var path = require('path');

http
  .createServer(function (req, res) {
    if (req.url === '/') {
      var filePath = path.join(__dirname, 'index.html');
      fs.readFile(filePath, function (error, content) {
        if (error) {
          res.writeHead(500, { 'Content-Type': 'text/plain' });
          res.end('Internal Server Error');
        } else {
          res.writeHead(200, { 'Content-Type': 'text/html' });
          res.end(content);
        }
      });
    } else if (req.url === '/runTest') {
      // Handle '/runTest' endpoint
      testRun()
        .then((response) => {
          res.writeHead(200, { 'Content-Type': 'text/plain' });
          res.end(response);
        })
        .catch((err) => {
          console.error('Test run failed:', err);
          res.writeHead(500, { 'Content-Type': 'text/plain' });
          res.end('Test run failed');
        });
    } else {
      // Handle other requests
      res.writeHead(404, { 'Content-Type': 'text/plain' });
      res.end('Not Found');
    }
  })
  .listen(8080, function () {
    console.log('HTTP server started on port 8080');
  });

('use strict');

const { JavaCaller } = require('java-caller');

async function testRun() {
  try {
    const response = await runExample();
    return response;
  } catch (err) {
    console.error('Unexpected error: ' + err.message + '\n' + err.stack);

    process.exitCode = 1;
  }
}

async function runExample() {
  const java = new JavaCaller({
    classPath: './java',
    mainClass: 'Main',

    minimumJavaVersion: 10,
  });

  const { status, stdout, stderr } = await java.run([
    '-a',
    'list',
    '--of',
    'arguments',
  ]);

  console.log(`Java status code is ${status}`);
  let response = stdout;
  if (stdout) {
    // console.log('Java stdout:\n' + stdout);
  }

  if (stderr) {
    console.log('Java stderr:\n' + stderr);
  }
  return response;
}
