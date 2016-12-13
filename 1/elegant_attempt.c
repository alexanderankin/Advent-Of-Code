#include <stdio.h>
#include <stdlib.h>

enum _direction {
  _dir_UP, _dir_RIGHT, _dir_DOWN, _dir_LEFT
};

char *_direction_strings[4] = {
  "_dir_UP", "_dir_RIGHT", "_dir_DOWN", "_dir_LEFT"
};

typedef enum _direction direction;

struct _state
{
  int x;
  int y;
  direction d;
};

typedef struct _state state;

enum _instruction_direction {
  _i_dir_RIGHT, _i_dir_LEFT
};

typedef enum _instruction_direction instruction_direction;

struct _instruction
{
  int length;
  instruction_direction d;
};

typedef struct _instruction instruction;

void move(state*, instruction*);
void print_state(state *);
int next(instruction *);
int abs(int);
int abs(int i)
{
  return i < 0 ? i * -1 : i;
}

int main(int argc, char const *argv[])
{
  state s;
  state *s_pointer = &s;
  s_pointer->x = 0;
  s_pointer->y = 0;
  s_pointer->d = 0;
  instruction *test = malloc(sizeof(*test));

  // test->d = _i_dir_RIGHT;
  // test->length = 2;
  // move(s_pointer, test);
  // test->d = _i_dir_LEFT;
  // test->length = 3;
  // move(s_pointer, test);

  // test->d = _i_dir_RIGHT;
  // test->length = 2;
  // move(s_pointer, test);
  // test->d = _i_dir_RIGHT;
  // test->length = 2;
  // move(s_pointer, test);
  // test->d = _i_dir_RIGHT;
  // test->length = 2;
  // move(s_pointer, test);
  // test->d = _i_dir_RIGHT;
  // test->length = 3;
  // move(s_pointer, test);

  // test->d = _i_dir_RIGHT;
  // test->length = 5;
  // move(s_pointer, test);
  // test->d = _i_dir_LEFT;
  // test->length = 5;
  // move(s_pointer, test);
  // test->d = _i_dir_RIGHT;
  // test->length = 5;
  // move(s_pointer, test);
  // test->d = _i_dir_RIGHT;
  // test->length = 3;
  // move(s_pointer, test);

  while (!next(test)) { move(s_pointer, test); printf("\n"); }
  move(s_pointer, test);

  printf("\n");
  print_state(s_pointer);

  printf("The Total Distance is %d\n", abs(s_pointer->x) + abs(s_pointer->y));
  free(test);
  return 0;
}

char buf[10];
int next(instruction *inst)
{
  for (int i = 0; i < sizeof(buf); ++i)
  {
    buf[i] = '\0';
  }

  char lol;
  inst->d = ((lol = getchar()) == 'L' ? _i_dir_LEFT : _i_dir_RIGHT);
  // printf("***%c***\n", lol);
  // printf("***%d***\n", lol == 'L');
  // printf("***%d***\n", inst->d);

  int i = 0;
  int c;
  while((c = getchar()) != ',' && c != EOF)
  {
    buf[i++] = (char) c;
  }
  // printf("%d\n", atoi(buf));
  // printf("%s\n", buf);

  inst->length = atoi(buf);
  printf("%c%d, ", ((inst->d == -_i_dir_RIGHT) ? 'R' : 'L'), (inst->length));

  return getchar() == EOF;
}

void move(state *s, instruction *i)
{
  printf(" (direction was %s, ", _direction_strings[s->d]);
  s->d = (s->d + (i->d ? -1 : 1)) % 4;
  printf("direction is %s) ", _direction_strings[s->d]);
  switch (s->d) {
    case _dir_UP:
      s->y += i->length;
      break;
    case _dir_RIGHT:
      s->x += i->length;
      break;
    case _dir_DOWN:
      s->y -= i->length;
      break;
    case _dir_LEFT:
      s->x -= i->length;
      break;
  }
}

void print_state(state *s)
{
  printf("state: x: %-3d, y: %-3d, d: %d\n",
    s->x, s->y, s->d);
}
