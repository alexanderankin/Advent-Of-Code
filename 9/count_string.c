#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <ctype.h>
#include <string.h>
#include <errno.h>

static int atoin(char *, int, int);
// int count_string(char *, int);
// int count_string(char *string, int length)
// {
//   int result = 0, i = 0,
//       quantifier_start
  
//   while((string[i] != '(') && i++ < length)
//     result++;

//   quantifier_start = i;
//   while((string[i] != 'x') && i++ < length)
//     ;

//   char *temp;
//   temp = malloc(i - quantifier_start)
//   int quantifier = atoi(strndup())

//   while((string[i] != ')') && i++ < length)

  
//   return result;
// }

static int atoin(char *string, int start, int end)
{
  char *buffer = malloc(end - start + 1);
  int buffer_pointer = 0;
  for (int i = start; i < end; ++i)
  {
    buffer[buffer_pointer++] = string[i];
    // printf("%c", buffer[buffer_pointer - 1]);
  }
  
  buffer[end - start] = '\0';
  int result = atoi(buffer);
  // printf("%s\n", buffer);
  free(buffer);
  
  return result;
}

int main(int argc, char const *argv[])
{
  // int result = count_string("(3x3)XYZ");
  int result = atoin("(3x3)XYZ", 3, 4);
  printf("result: %d\n", result);
  return 0;
}