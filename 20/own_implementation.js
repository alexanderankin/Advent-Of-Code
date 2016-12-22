var fs = require('fs');

function getFileContents(path, cb) {
  fs.readFile(path, {
    encoding: 'ascii'
  }, cb);
}

function test_ip(ip, ranges) {
  /*console.log("ip", ip);
  console.log("ranges", ranges);*/

  for (var i = 0; i < ranges.length; i++) {
    /*console.log("range0", ranges[i][0], "ip", ip,
      "range1", ranges[i][1]);*/

    if (ip >= ranges[i][0] && ip <= ranges[i][1])
      /*console.log("inside")*/
      return false;
  }
  return true;
}

/*(function test_test_ip(inputfilename) {
  getFileContents(inputfilename, function (err, data) {
    var ivals = data
      .split('\n')
      .map((line) => line.split('-')
        .map((part) => parseInt(part)))
      .sort(function (ival_a, ival_b) {
        return ival_a[0] - ival_b[0]
      });
    console.log(test_ip(8, ivals));
  });
})('./test');*/

function main(inputfilename) {
  getFileContents(inputfilename, function (err, data) {
    var ivals = data
      .split('\n')
      .map((line) => line.split('-')
        .map((part) => parseInt(part)))
      .sort(function (ival_a, ival_b) {
        return ival_a[0] - ival_b[0]
      });

    // var max_val = Math.pow(2, 32) - 1;

    var minvals = ivals.map(function (ival) {
      return ival[1] + 1;
    });

    console.log(
    minvals.filter(function (ip) {
      console.log("ip", ip, " passes test fn", 
        test_ip(ip, ivals));
      return test_ip(ip, ivals);
    }).map(function (ip) {
      console.log("ip", ip, "has passed.");
      // process.exit(0);  // part one
    })
    .length);

  });
}

main('./input');
