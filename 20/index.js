var fs = require('fs');

function getFileContents(path, cb) {
  fs.readFile(path, {
    encoding: 'ascii'
  }, cb);
}

function main() {
  getFileContents('./input', function (err, data) {
    var lines = data.split('\n');

    lines = lines.map((l) => l.split('-'))
      .map((arr) => arr.map((x) => parseInt(x)));

    lines.map(function (l) {
      addToIntervals(l);
      // console.log(l);
    });

    // console.log(intervals);
    var minvalue = find_min_incl(intervals, Math.pow(2, 32) - 1);
    console.log(minvalue);
    // console.log(find_min_incl(intervals, 9));
    var ip = getipfromint(minvalue);
    console.log("ip", ip);
    console.log("ip", ip.join('.'));
  });
}

main();

intervals = [];

function addToIntervals(interval) {
  var i;
  if ((i = overlapsExisting(interval)) !== false) {
    intervals[i][0] = Math.min(interval[0], intervals[i][0]);
    intervals[i][1] = Math.max(interval[1], intervals[i][1]);

  } else {
    intervals.push(interval);
  }
}

function overlapsExisting(interval) {
  for (var i = 0; i < intervals.length; i++) {
    var x1 = intervals[i][0];
    var x2 = intervals[i][1];
    var y1 = interval[0];
    var y2 = interval[1];
    if (is_overlapping(x1,x2,y1,y2))
      return i;
  }

  return false;
}

function is_overlapping(x1,x2,y1,y2) {
  return Math.max(x1,y1) <= Math.min(x2,y2);
}
/** tests */
// console.log(overlapsExisting([ 4, 7 ]));

/** more tests */
// addToIntervals([4, 7]);
// console.log(intervals);

function find_min_incl(excl, max) {
  excl.sort(function compare_interval_start(a, b) {
    return a[0] - b[0];
  });

  var counter = 0;
  var offset = 0;
  while (true) {
    if (excl[offset][0] <= counter) {
      counter += excl[offset][1] - excl[offset][0];
      offset++;
    }

    // console.log("offset", offset);
    console.log("counter", counter);
    // console.log(excl[offset]);
    return counter;
  }
}

function getipfromint(int) {
  var ip = []; 
  ip.unshift(int % 256);
  int -= (int % 256);
  int /= 256;
  ip.unshift(int % 256);
  int -= (int % 256);
  int /= 256;
  ip.unshift(int % 256);
  int -= (int % 256);
  int /= 256;
  ip.unshift(int % 256);
  return ip;
}

// console.log(getipfromint(4294967295));