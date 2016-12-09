#!/usr/bin/env python3

import sys

def solution(line):
  """ It was here that i realized that alas,
  I had opened up sublime and begun coding the day
  before yesterdays challenge, moved it over a couple
  of days, and moved on to day 9. This one was 
  supposed to be the one with the checksums, i
  think it was day 6, so i put there, but im not
  sure at the time of writing."""

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

def process_input(file_handle):
  for line in file_handle:
    pass
