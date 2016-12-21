var fs = require('fs');

function getFileContents(path, cb) {
  fs.readFile(path, {
    encoding: 'ascii'
  }, cb);
}

// getFileContents('./test', function (err, data) {
//  console.log(data.substring(0, 10));
// });

var command_expressions = require('./command_expressions');

function assignCommand(line) {
  for (i in command_expressions) {
    if (command_expressions[i].regex.test(line)) {
      return {
        subroutine: command_expressions[i].subroutine,
        args: command_expressions[i].regex.exec(line).slice(1)
      }
    }
  }
}

getFileContents('./input', function (err, data) {
  var lines = data.split('\n');
  var input = lines.shift();
  console.log('input is', input);

  var output = lines.map(function (line) {
    var assignedCommand = assignCommand(line);
    var sr     = assignedCommand.subroutine;
    var args   = assignedCommand.args;

    return function(input) { return sr.apply(null, [input].concat(args))};
  }).reduce(function function_name(string, next) {
    return next(string);
  }, input);

  console.log(output);
});
