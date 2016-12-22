function swap_positions(input, pos_b, pos_a) {
  var arr = input.split('');
  var temp = arr[pos_b];
  arr[pos_b] = arr[pos_a];
  arr[pos_a] = temp;
  return arr.join('');
};

// var input  = "abcdefg"
// var input  = "aecdbfg"
// console.log(input);
// var output = swap_positions(input, 4, 1);
// console.log(output);

function swap_letters(input, let_b, let_a) {
  let_b = input.indexOf(let_b);
  let_a = input.indexOf(let_a);

  var arr = input.split('');
  var temp = arr[let_b];
  arr[let_b] = arr[let_a];
  arr[let_a] = temp;
  return arr.join('');
};

// var input  = "abcdefg"
// var output = swap_letters(input, 'a', 'f');
// console.log(output);

function rotate_number(input, direction, number) {
  var arr = input.split('');
  if (direction != 'right') {
    for (var i = number - 1; i >= 0; i--) {
      arr.unshift(arr.pop());
    }
  } else {
    for (var i = number - 1; i >= 0; i--) {
      arr.push(arr.shift());
    }
  }
  return arr.join('');
}

// var input  = "abcdefg"
// var output = rotate_number(input, 'right', 3);
// console.log(output);

function rotate_based_on_p(input, letter) {
  input = rotate_number(input, 'right', 2);  // actually left

  var idx = input.indexOf(letter);
  input = rotate_number(input, 'right', idx + 1 + (idx >= 4 ? 1 : 0 ));
  return input;
}

// var input  = "abcdefg"
// var output = rotate_number(input, 'right', 3);
// console.log(output);

function reverse_from_to(input, from_idx, to_idx) {
  var arr = input.split('');
  for (; from_idx < to_idx; (from_idx++, to_idx--)) {
    var temp = arr[to_idx];
    arr[to_idx] = arr[from_idx];
    arr[from_idx] = temp;
  }
  return arr.join('');
}

// var input  = "abcde";
// var output = reverse_from_to(input, 1, 3);
// console.log(output);

function insert(input, from_idx, to_idx) {
  var arr = input.split('');
  var ele = arr.splice(from_idx, 1)[0];
  arr = arr.slice(0, to_idx).concat(ele).concat(arr.slice(to_idx));
  return arr.join('');
}

// var input  = "abcde";
// var output = insert(input, 1, 3);
// console.log(output);

var command_expressions = {
  swap_p: {
    regex: /swap position ([\d]+) with position ([\d]+)/,
    subroutine: swap_positions
  },
  swap_l: {
    regex: /swap letter ([\w]) with letter ([\w])/,
    subroutine: swap_letters
  },
  rotate_n: {
    regex: /rotate (left|right) ([\d]+) step(s)*/,
    subroutine: rotate_number
  },
  rotate_p: {
    regex: /rotate based on position of letter ([\w])/,
    subroutine: rotate_based_on_p
  },
  reverse: {
    regex: /reverse positions ([\d]+) through ([\d]+)/,
    subroutine: reverse_from_to
  },
  insert: {
    regex: /move position ([\d]+) to position ([\d]+)/,
    subroutine: insert
  }
};

module.exports = command_expressions;
