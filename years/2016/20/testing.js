var value = 22887907;

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

var valueip = getipfromint(value);

console.log(valueip);
