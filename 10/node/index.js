var fs = require('fs');

var input = fs.createReadStream('./test');

input.on('data', function (chunk) {
  var s = chunk.toString();
  // console.log("chunk:", s);
  console.log("lio", s.lastIndexOf('\n'));
  console.log("len", s.length);
  // (value_re.exec(s) || []).map(function () {
  //   console.log(arguments);
  // });
  var matches = [];
  var m;
  while (true) {if ((m = value_re.exec(s)) == null) break; matches.push(m);}

  console.log(matches);

  // (s.match(value_re) || []).map(function () {
  //   console.log(arguments);
  // });

});


// var value_re = /^value ([0-9]+) goes to bot ([0-9]+)$/;
var value_re = /value ([0-9]+) goes to bot ([0-9]+)/g;
var rule_re = /bot ([0-9]+) gives low to (bot|output) ([0-9]+) and high to (bot|output) ([0-9]+)/g;


var util = require('util');
var EventEmitter = require('events').EventEmitter;
function Bot() {
  Bot.super_.apply(this, arguments);

}
util.inherits(Bot, EventEmitter);