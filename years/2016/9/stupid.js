var test = ("X(8x2)(3x3)ABCY");
var expected = 7;

var id1;
var id2;
var countedLength = 0;
for (var i = 0; i < test.length; i++) {
  if (test[i] == "(") {
    id1 = test.indexOf("x", ++i);
    // console.log("test.substring(i, id1)", test.substring(i, id1));
    console.log("size", test.substring(i, id1));
    size = parseInt(test.substring(i, id1), 10);
    console.log("size", size);
    
    id2 = test.indexOf(")", ++id1);
    // console.log("test.substring(id1, id2)", test.substring(id1, id2));
    console.log("reps", test.substring(id1, id2));
    repetitions = parseInt(test.substring(id1, id2), 10);
    console.log("repetitions", repetitions);

    console.log("additional chars is", ( size * ( repetitions - 1 ) ) );
    console.log("total chars (w/rep)", ( size * ( repetitions ) ) );
    countedLength += ( size * ( repetitions ) );

    i = id2 + repetitions;
    console.log("i is now", i);
    console.log("charat i", test[i]);
    // console.log("id1 is now", id1);
    // console.log("charat id1", test[id1]);
    // console.log("id2 is now", i);
    // console.log("charat id2", test[id2]);
  } else {

    console.log("now counting character", test[i]);
    countedLength++;
  }
}

console.log(countedLength);