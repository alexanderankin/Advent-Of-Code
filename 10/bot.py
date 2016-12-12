# class Bot(object):
# 	"""docstring for Bot"""
# 	def __init__(self):
# 		super(Bot, self).__init__()
# 		self.val1 = None
# 		self.val2 = None
	
# 	def give(self, value):
# 		if self.val1 == None:
# 			self.val1 = value
# 		else:
# 			self.val2 = value
# 			raise Exception()

# 	def getExtra(self):
# 		return self.val3

	#

#

class Bot(object):
	"""docstring for Bot"""
	def __init__(self):
		super(Bot, self).__init__()
		self.val = None
	
	def give(self, value):
		if self.val == None:
			self.val = value
			return None
		else:
			self.too_high = value > self.val
			return value

	def getExtra(self):
		return self.val3

	#

#
