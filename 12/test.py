from index import Instruction, AsmbunnyRegisters, run_file

def cpyInstructionTest():
  """ Tests the instruction cpy"""
  r = AsmbunnyRegisters();
  i = Instruction("cpy 2 a")
  i.run(r)
  # r.print_state()
  # print(r.a)
  assert(r.a == 2)
  i = Instruction("cpy a b")
  i.run(r)
  assert(r.b == r.a)
  assert(r.c == 0)
  assert(r.d == 0)

def incInstructionTest():
  """ Tests the instruction inc"""
  r = AsmbunnyRegisters();
  i = Instruction("inc a")
  i.run(r)
  assert(r.a == 1)
  i = Instruction("inc b")
  i.run(r)
  assert(r.b == 1)
  i = Instruction("inc b")
  i.run(r)
  assert(r.b == 2)

def decInstructionTest():
  """ Tests the instruction dec"""
  r = AsmbunnyRegisters();
  i = Instruction("dec a")
  i.run(r)
  assert(r.a == -1)
  i = Instruction("dec b")
  i.run(r)
  assert(r.b == -1)
  i = Instruction("dec b")
  i.run(r)
  assert(r.b == -2)
  pass
def jnzInstructionTest():
  """ Tests the instruction jnz"""
  pass


def instructionTest():
  i = Instruction("cpy 2 a")
  r = AsmbunnyRegisters();
  assert (1 == i.run(r))
  r.print_state()
  print(r.__dict__)
  
  i = Instruction("cpy a b")
  assert (1 == i.run(r))
  r.print_state()
  print(r.__dict__)


def testcase(file_location):
  run_file(file_location)

def main():
  # instructionTest()
  # cpyInstructionTest()
  # incInstructionTest()
  # decInstructionTest()
  testcase('./test')



if __name__ == '__main__':
  main()