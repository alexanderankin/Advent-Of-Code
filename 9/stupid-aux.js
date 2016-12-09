var real = function getRealToken(str, startindex) {
  var debug = arguments[2] || false;

  var id4;
  id4 = str.indexOf("(", startindex) + 1;
  if (debug) {console.log(startindex);}
  if (debug) {console.log(id4);}

  var id1; // end quantifier
  id1 = str.indexOf("x", id4);
  var quantifier = parseInt(str.substring(id4, id1));
  if (debug) {console.log("quantifier", quantifier);}

  var id2; // start repeater
  id2 = id1 + 1;

  var id3; // end repeater
  id3 = str.indexOf(")", id1);
  var repeater = parseInt(str.substring(id2, id3));
  if (debug) {console.log("repeater", repeater);}

  var id5; // end token
  id5 = id3 + 1; // the close paren
  id5 = id5 + quantifier;

  if (debug) {console.log("startindex was ", startindex, str[startindex]);}
  if (debug) {console.log("after index now", id5, str[id5]);}

  return {
    counterIncrement: id5 - startindex,
    quantifier: quantifier,
    repeater: repeater,
    generatedchars: quantifier * repeater
  }
}

module.exports = real;

// var result = real("abc(4x5)def(5x5)", 2);
// console.log(result);

// var test = ("ADVENT");
// var test = ("A(1x5)BC");
// var test = ("X(8x2)(3x3)ABCY");
// var test = ("(6x1)(1x3)A");
var test = require('./test');
var countedLength = 0;
for (var i = 0; i < test.length; i++) {
  if (test[i] == "(") {
    var tokeninfo = real(test, i);
    i += tokeninfo.counterIncrement;
    i -= 1; // counteract i++
    // console.log(tokeninfo);
    countedLength += tokeninfo.generatedchars;
  }

  else {
    countedLength++;
  }
}
console.log(countedLength);