#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <ctype.h>
#include <string.h>
#include <errno.h>

#include "count_string.h"

void assertions(void);

int main(int argc, char const *argv[])
{
  /*int result = count_string(
    "(3x3)ABC(2x3)XY(5x2)PQRST",
    strlen("(3x3)ABC(2x3)XY(5x2)PQRST"),
    0);
  printf("result is %d\n", result);*/
  int result = count_string("(3x3)ABCY", strlen("(3x3)ABCY"), 0);
  printf("result is %d\n", result);
  // assertions();
  return 0;
}

void assertions(void)
{
  assert(count_string("XYZ", strlen("XYZ"), 0) == 3);
  // assert(count_string("(3x3)XYZ", strlen("(3x3)XYZ"), 0) == 9);
  assert(count_string(
    "(3x3)ABC(2x3)XY(5x2)PQRST",
    strlen("(3x3)ABC(2x3)XY(5x2)PQRST"),
    0) == (9 + 6 + 10));

}
