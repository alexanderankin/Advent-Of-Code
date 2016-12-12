
INSTRUCTIONTYPES = ['cpy', 'inc', 'dec', 'jnz']
REGISTERTYPES    = ['a',   'b',   'c',   'd'  ]
class Instruction(object):
  """Parses a line of text into assembunny instruction"""
  def __init__(self, line):
    super(Instruction, self).__init__()
    self.line = line
    self.valid()

  def valid(self):
    parts = self.line.strip().split(" ")
    if not (len(parts) is 2 or len(parts) is 3):
      raise Exception("Wrong length Instruction")

    if parts[0] not in INSTRUCTIONTYPES:
      raise Exception("Invalid Instruction Type", parts[0])

    # actual logic is more complex
    if parts[1].isalpha() and parts[1] not in REGISTERTYPES:
      raise Exception("First Argument of instruction invalid",
        parts[1], self.line)

    # actual logic is more complex
    if len(parts) == 3:
      if parts[2].isalpha() and parts[2] not in REGISTERTYPES:
        raise Exception("First Argument of instruction invalid",
          parts[2], self.line)

    self.parts = parts
    return True

  def run(self, registers):
    if (self.parts[0] == 'inc' or self.parts[0] == 'dec'):
      registers[self.parts[1]] = registers[self.parts[1]] + (
          1 if self.parts[0] == 'inc' else -1
        )
      return 1

    elif (self.parts[0] == 'jnz'):
      self.parts[1] = str(self.parts[1])
      if self.parts[1].isdigit():
        if int(self.parts[1]) == 0:
          return 1
        else:
          return int(self.parts[2])
      else:
        # print("here")
        # print(type(self.parts[1]))
        # print(self.parts[1])
        if registers[self.parts[1]] != 0:
          return int(self.parts[2])
        else:
          return 1
        

      if registers[self.parts[1]].isalpha():
        if registers[self.parts[1]] != 0:
          return int(self.parts[2])
        else:
          return 1
      elif self.parts[1] == 0:
        return int(self.parts[2])
      else:
        return 1

    elif (self.parts[0] == 'cpy'):
      if self.parts[1].isalpha():
        value = registers[self.parts[1]]
      else:
        value = self.parts[1]

      registers[self.parts[2]] = value
      return 1


class AsmbunnyRegisters(object):
  """docstring for AsmbunnyRegisters"""
  def __init__(self):
    super(AsmbunnyRegisters, self).__init__()
    self.a = 0
    self.b = 0
    self.c = 0
    self.d = 0

  def processInstruction(self, ins):
    """ Returns how many to move forward. """
    if not isinstance(ins, Instruction):
      raise Exception("Expected Instruction")

    return ins.run(self)

  def __getitem__(self, key):
    return self.__dict__[key]

  def __setitem__(self, key, value):
    self.__dict__[key] = int(value)

  def print_state(self):
    print ("Registers < a:[%s], b:[%s], c:[%s], d:[%s]>"
      % (self.a, self.b, self.c, self.d))

  def string_state(self):
    return ("Registers < a:[%s], b:[%s], c:[%s], d:[%s]>"
      % (self.a, self.b, self.c, self.d))

def run_file(file_location):
  instructions = []
  with open(file_location) as testfile:
    for line in testfile:
      instructions.append(Instruction(line))
    testfile.close()

  r = AsmbunnyRegisters()

  code_pointer = 0
  while code_pointer < len(instructions):
    print("On code_pointer: %d, at instruction: %-10s. s: %s" % \
      (code_pointer, instructions[code_pointer].line.strip(), r.string_state()))
    code_pointer += instructions[code_pointer].run(r)
  
  r.print_state()

def main():
  run_file('./input')

if __name__ == '__main__':
  main()