class Rules(object):
	"""docstring for Rules"""
	def __init__(self):
		super(Rules, self).__init__()
		self.rules = []
	
	def append(rule):
		rules.append(rule)

	def getruleforbot(self, bot):
		for rule in self.rules:
			if int(rule['bot_gives']) == bot:
				return rule

	#

#
