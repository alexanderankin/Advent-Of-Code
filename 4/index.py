#!/usr/bin/env python3

import sys
from collections import defaultdict
import re

def sort_letter_pairs(pairs):
  """
  [('a', 2), ('e', 1), ('m', 1), ('l', 1), ('o', 3), ('n', 1), ('r', 2), ('t', 1)]
  [('o', 3), ('a', 2), ('r', 2), ('e', 1), ('m', 1), ('l', 1), ('n', 1), ('t', 1)]
  [('o', 3), ('a', 2), ('r', 2), ('e', 1), ('l', 1), ('m', 1), ('n', 1), ('t', 1)]
  """
  pairs = sorted(pairs, key=lambda pair: pair[1], reverse=True)

  # print(pairs)

  prev_frequency_count = pairs[0][1]
  curr_frequency_count = 0 # will be reinitialized
  start_of_group = 0
  for i in range(1, len(pairs)):
    curr_frequency_count = pairs[i][1]
    if (curr_frequency_count == prev_frequency_count) and i != (len(pairs) - 1):
      continue
    else:
      if i == (len(pairs) - 1):
        i += 1
      # process group before i pointer
      pairs[start_of_group:i] = sorted(pairs[start_of_group:i],
                                           key=lambda pair: pair[0],
                                           reverse=False)

      prev_frequency_count = curr_frequency_count
      # print("grouping start %d (%s) end %d (%s)" % (
      #   start_of_group, str(pairs[start_of_group]),
      #   i, str(pairs[i]))
      # )
      start_of_group = i
  
  return pairs

class Solution(object):
  """Advent of Code Day 4"""
  def __init__(self):
    super(Solution, self).__init__()
    self.regex = r"\[([a-z]{5})\]$"

  def giveInput(self, input):
    self.input = input

  def getChecksum(self):
    match = re.search(self.regex, self.input)
    match_group = match.group()
    match_group = match_group[1:len(match_group) - 1]
    return match_group

  def isRealRoom(self):
    letter_counts = defaultdict(lambda: 0)
    for char in self.input:
      if char.isdigit():
        break
      elif char.isalpha():
        letter_counts[char] = letter_counts[char] + 1;
      else:
        continue
    items = letter_counts.items()
    # print(items)

    items = sort_letter_pairs(items)
    # print(items)

    calculatedChecksum = ""
    for x in xrange(5):
      calculatedChecksum += items[x][0]

    # print (calculatedChecksum)
    # print (self.getChecksum())
    return calculatedChecksum == self.getChecksum()

  def getSectorID(self):
    last_dash = 0
    end_of_si = 0 # end_of_sector_id
    for x in range(len(self.input)):
      if self.input[x] == "-":
        last_dash = x
      elif self.input[x] == "[":
        end_of_si = x
        break

    # print(self.input[last_dash + 1:end_of_si])
    return int((self.input[last_dash + 1:end_of_si]))


# sol = Solution()
# sol.giveInput("aaaaa-bbb-z-y-x-123[abxyz]")
# sol.giveInput("a-b-c-d-e-f-g-h-987[abcde]")
# sol.giveInput("not-a-real-room-404[oarel]")
# sol.giveInput("totally-real-room-200[decoy]")
# valid = sol.isRealRoom()
# print(valid)
# sol.getSectorID()
# chks = sol.getChecksum()
# print(chks)

# exit(0)

def process_input(file_handle):
  sum = 0
  for line in file_handle:
    sum += run_solution(line)

  print (sum)

solution = Solution()
def run_solution(line):
  solution.giveInput(line)
  if solution.isRealRoom():
    return solution.getSectorID()
  return 0

argument_array = sys.argv
if len(argument_array) is not 2:
  print("Usage: " + argument_array[0] + " <input | '-stdin' >")
  exit(1)


if argument_array[1] is '-stdin':
  usingstdin = True
else:
  usingstdin = False
  file = argument_array[1]

if not usingstdin:
  with open(file) as file_handle:
    process_input(file_handle)
else:
  process_input(sys.stdin)

