
from index import Solution

cases = [
  {
    "input": "aaaaa-bbb-z-y-x-123[abxyz]",
    "valid": True,
    "sector": 123,
    "sum": "abxyz"
  },
  {
    "input": "a-b-c-d-e-f-g-h-987[abcde]",
    "valid": True,
    "sector": 987,
    "sum": "abcde"
  },
  {
    "input": "not-a-real-room-404[oarel]",
    "valid": True,
    "sector": 404,
    "sum": "oarel"
  },
  {
    "input": "totally-real-room-200[decoy]",
    "valid": False,
    "sector": 200,
    "sum": "decoy"
  }
]

def test_part_one():
  sol = Solution()
  for case in cases:
    sol.giveInput(case['input'])
    assert(sol.isRealRoom() == case['valid'])
    assert(sol.getSectorID() == case['sector'])
    assert(sol.getChecksum() == case['sum'])
    print("assertions passed for " + str(case['input']))

def test_part_two():
  sol = Solution()
  sol.giveInput("qzmt-zixmtkozy-ivhz-343")
  print(sol.decrypt())
  # assert(sol.decrypt() == "very encrypted name")


if __name__ == '__main__':
  test_part_two()
