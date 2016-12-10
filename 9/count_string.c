#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <ctype.h>
#include <string.h>
#include <errno.h>

#include "count_string.h"

static int static_i = 3;

static int atoin(char *, int, int);
int count_string(char *string, int length, int offset)
{
  int result = 0, i = offset,
      quantifier_start,
      multiplier_start;

  /*for (int j = 0; j < length; ++j)
  {
    printf("%c", string[j + offset]);
  }
  printf("\n");*/

  while((string[i] != '(') && i < (length + offset))
  {
    printf("counting %4c at %d (i%d, l%d, f%d\n", string[i], i, i, length, offset);
    result++;
    i++;
  }

  if (i == length + offset) return result;
  /*printf("i(%d) - string[i](%c) length(%d) offset(%d)\n",
          i,      string[i],    length,    offset);*/

  quantifier_start = i + 1;
  while((string[i] != 'x') && i < (length + offset))
    i++;

  // printf("qstart %d has char %c\n", quantifier_start, string[quantifier_start]);
  /*printf("index qend %d has char %c\n", i, string[i]);*/

  int quantifier = atoin(string, quantifier_start, i);
  printf("quantifier is %d\n", quantifier);

  multiplier_start = i + 1;
  // printf("mstart %d has char %c\n", multiplier_start, string[multiplier_start]);
  while((string[i] != ')') && i < (length + offset))
    i++;

  i += 1;
  // printf("multiplier_start %d, i %d cat i %c\n", multiplier_start, i, string[i]);
  int multiplier = atoin(string, multiplier_start, i + offset);
  printf("multiplier is %d\n", multiplier);

  for (int j = 0; j < multiplier; ++j)
  {
    // printf("string is %s, string[i] %c%c, qer %d\n", string, string[i], string[i+1], quantifier);
    result += count_string(string, quantifier, i);
  }

  /** experimental */
  // for (; i < length; ++i)
  // {
  //   printf("%c", string[i]);
  // }
  // printf("\n");
  // printf("%d\n", quantifier);
  // if (static_i-- > -1)
  if (length - i + quantifier > 0)
  {
    // printf("length - i + quantifier: %d, i: %d\n", length - i + quantifier, i);
    int temp;
    printf("result %d\n", result);
    result += (temp = count_string(string, length - i + quantifier, i + quantifier));
    printf("temp %d, from s%s, l%d o%d\n", temp, string, length - i + quantifier, i + quantifier);
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
