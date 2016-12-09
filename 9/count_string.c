#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <ctype.h>
#include <string.h>
#include <errno.h>

static int atoin(char *, int, int);
int count_string(char *, int, int);
int count_string(char *string, int length, int offset)
{
  int result = 0, i = offset,
      quantifier_start,
      multiplier_start;
  
  while((string[i] != '(') && i < (offset + length))
  {
    printf("counting %c at %d\n", string[i], i);
    result++;
    i++;
  }

  printf("i(%d) - 1 (%d) = (offset + length)(%d)\n", i, i - 1, (offset + length));
  if (i - 1 == (offset + length)) return result;

  quantifier_start = i + 1;
  while((string[i] != 'x') && i++ < (offset + length))
    ;
  
  // printf("index qstart %d has char %c\n",
  //   quantifier_start,
  //   string[quantifier_start]);
  // printf("index qend %d has char %c\n",
  //   i - 1,
  //   string[i - 1]);

  int quantifier = atoin(string, quantifier_start, i);
  printf("quantifier is %d\n", quantifier);

  multiplier_start = i + 1;
  // printf("index multiplier_start %d has char %c\n",
  //   multiplier_start,
  //   string[multiplier_start]);
  while((string[i] != ')') && i++ < (offset + length))
    ;
  int multiplier = atoin(string, multiplier_start, i);
  printf("multiplier is %d\n", multiplier);
  
  for (int j = 0; j < multiplier; ++j)
  {
    result += count_string(string, quantifier, i);
  }

  return result;
}

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
  // int result = count_string("(3x3)XYZ", strlen("(3x3)XYZ"), 0);
  // int result = count_string("XYZ", strlen("XYZ"), 0);
  // int result = atoin("(3x3)XYZ", 3, 4);
  int result = count_string("X(8x2)(3x3)ABCY", strlen("X(8x2)(3x3)ABCY"), 0);
  printf("result: %d\n", result);
  return 0;
}