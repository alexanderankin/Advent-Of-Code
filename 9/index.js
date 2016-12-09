var fs = require('fs');

var inputFile = fs.createReadStream('./inputFile');
inputFile.on('data', function (chunk) {
  nextPiece = chunk.toString();
  console.log(nextPiece);
});

function (chunk) {
  var length = chunk.length;
  for (var i = 0; i < length; ++i) {
    if (chunk[i] == '(') {}
  }
}