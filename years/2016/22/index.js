var fs = require('fs');

function getFileContents(path, cb) {
  fs.readFile(path, {
    encoding: 'ascii'
  }, cb);
}

var fields_exp = /\/dev\/grid\/node-x([\d]+)\-y([\d]+) +([\d]+)T +([\d]+)T +([\d]+)T +([\d]+)%/;
function getFields(line) {
  var matches = fields_exp.exec(line);
  matches = matches.slice(1);

  return matches;
}
/*var fields = getFields("/dev/grid/node-x0-y0     94T   65T    29T   69%");
console.log(fields);*/
