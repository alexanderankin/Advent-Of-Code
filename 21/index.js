var fs = require('fs');

function getFileContents(path, cb) {
  fs.readFile(path, {
    encoding: 'ascii'
  }, cb);
}

// getFileContents('./test', function (err, data) {
//  console.log(data.substring(0, 10));
// });

var command_expressions = require('command_expressions');

function assignCommand(line) {
  for (i in command_expressions) {
    if (command_expressions[i].regex.test(line)) {
      // console.log(command_expressions[i].exec(line));
      // console.log(command_expressions[i].regex.exec(line).slice(1));
      // spy.call(spy, [i].concat(command_expressions[i].exec(line).slice(1)));
      return {
        subroutine: command_expressions[i].subroutine,
        args: command_expressions[i].regex.exec(line).slice(1)
      }
    }
  }
}

getFileContents('./test', function (err, data) {
  var lines = data.split('\n');
  var input = lines.shift();
  console.log('input is', input);
  lines.map(function match_line(line) {
    var cmd = assignCommand(line);
    console.log(line, cmd);
  });
});

function spy(arguments) {
  console.log(arguments);
  // require('repl').start().context.a = arguments;
}

var command_commands = {
  swap_p: /swap position ([\d]+) with position ([\d]+)/,
  swap_l: /swap letter ([\w]) with letter ([\w])/,
  rotate_n: /rotate (left|right) ([\d]+) steps/,
  rotate_p: /rotate based on position of letter ([\w])/,
  reverse: /reverse positions ([\d]+) through ([\d]+)/,
  insert: /move position ([\d]+) to position ([\d]+)/
};

// var commands = {
// 
// };

