var test = "A(1x5)BC";
var expected = 7;

var id1;
var id2;
for (var i = 0; i < test.length; i++) {
  if (test[i] == "(") {
    id1 = test.indexOf("x", i);
    // console.log("test.substring(i, id1)", test.substring(i + 1, id1));
    console.log("size", test.substring(i + 1, id1));
    size = parseInt(test.substring(i + 1, id1), 10);
    console.log("size", size);
    
    id2 = test.indexOf(")", id1);
    // console.log("test.substring(id1, id2)", test.substring(id1 + 1, id2));
    console.log("reps", test.substring(id1 + 1, id2));
    repetitions = parseInt(test.substring(id1 + 1, id2), 10);
    console.log("repetitions", repetitions);

    console.log("additional chars is", ( size * ( repetitions - 1 ) ) );

  }
}