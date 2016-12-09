#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <ctype.h>
#include <string.h>
#include <errno.h>
// #include <>

void process_token(void);

unsigned long long sum = 0;
FILE *file;
int main(int argc, char const *argv[])
{
  int c;

  file = fopen("./inputFile", "r");
  if (file == NULL) {
    printf("oh no\n");
    printf("%s\n", strerror(errno));

    return 1;
  }

  while ((c = getc(file)) != EOF) {
    printf(" (ch:%c) ", c);
    if (isalpha(c))
    {
      sum++;
    } else if (c == '(')
    {
      printf("\n");
      process_token();   
    }
  }
  printf("The sum is %llu\n", sum);

  fclose(file);
  return 0;
}

void process_token(void)
{
  int c;
  static char buffer[10];
  // int buffer_pointer = 0;

  int i;
  for (i = 0; i < 10; ++i)
  {
    if ((c = getc(file)) == 'x')
    {
      break;
    }
    buffer[i] = c;
  }

  int quantifier = atoi(strndup(buffer, i));
  printf("quantifier: %d\n", quantifier);

  for (i = 0; i < 10; ++i)
  {
    if ((c = getc(file)) == ')')
    {
      break;
    }
    buffer[i] = c;
  }

  int multiplier = atoi(strndup(buffer, i));
  printf("multiplier: %d\n", multiplier);

  char *argument_of_token = malloc(quantifier);

  for (i = 0; i < quantifier; ++i)
  {
    argument_of_token[i] = getc(file);
  }

  for (i = 0; i < multiplier; ++i)
  {
    for (int ii = quantifier - 1; ii > -1; ii--)
    {
      // printf("Un-getting %c\n", argument_of_token[ii]);
      ungetc(argument_of_token[ii], file);
    }
  }

  free(argument_of_token);
}
