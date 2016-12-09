#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <ctype.h>
#include <string.h>
#include <errno.h>
// #include <>

void process_token(void);
// void process_chunk(char *, int);

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
  return 0;
}

void process_token(void)
{
  int c;
  char buffer[10];
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
      printf("Un-getting %c\n", argument_of_token[ii]);
      ungetc(argument_of_token[ii], file);
    }
  }

  /*for (i = 0; i < 10; ++i)
  {
    if ((c = getc(file)) == 'x')
    {
      break;
    }
    buffer[i] = c;
  }
  printf("quantifier: %d\n", quantifier);*/

}

// void process_chunk(char *chunk, int length)
// {
//   char buf[10];
//   int buf_pointer;

//   int i;
//   for (i = 0; i < length; ++i)
//   {
//     if (isalpha(chunk[i]))
//     {
//       sum++;
//     }
//     else if (chunk[i] == '(')
//     {
//       buf_pointer = 0;
//       while ((buf[buf_pointer++] = chunk[i++]) != 'x')
//         ;
//       buf_pointer--;
//       break;
//     }
//   }

//   int quantifier = atoi(strndup(buf, buf_pointer));
//   printf("quantifier: %d\n", quantifier);

//   buf_pointer = 0;
//   while ((buf[buf_pointer++] = chunk[i++]) != ')')
//     ;
//   buf_pointer--;

//   int multiplier = atoi(strndup(buf, buf_pointer));
//   printf("multiplier: %d\n", multiplier);

//   char *argument = malloc(quantifier);
//   int j;
//   for (j = 0; j < quantifier; ++j)
//   {
//     argument[j] = chunk[i++];
//   }

//   char *generated = malloc(multiplier * quantifier);
//   int generated_pointer = 0;
//   for (int j = 0; j < multiplier; ++j)
//   {
//     for (i = 0; i < quantifier; ++i)
//     {
//       generated[generated_pointer++] = argument[i];
//     }
//   }

//   // for (; i < length; ++i)
//   // {
//     // 
//   // }
// }

// void process_chunk_token(char *, length);
