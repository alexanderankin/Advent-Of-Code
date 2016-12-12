from re import match

def getSpecification():
	specification = []
	with open('./test') as testfile:
		for line in testfile:
			specification.append(line)
	return specification

# first_line = specification[0]

def get_value_spec(line):
	m = match('value ([0-9]+) goes to bot ([0-9]+)', line)
	value_spec = {
		'bot': int(m.group(2)),
		'value': int(m.group(1))
	}
	return value_spec

def value_spec_toString(vs):
	return "bot[%s].give(%s);" % (vs['bot'], vs['value'])

def get_gives_spec(line):
	m = match('bot ([0-9]+) gives low to (bot|output) ([0-9]+) and high to (bot|output) ([0-9]+)', line)
	# for i in range(1, 6):
		# print(m.group(i),)
	gives_spec = {
		'bot_gives': int(m.group(1)),
		'low_to_type': int(m.group(2)),
		'low_to': int(m.group(3)),
		'high_to_type': int(m.group(4)),
		'high_to': int(m.group(5))
	}
	return gives_spec

def gives_spec_toString(vs):
	return "bot[%s] = %s;" % (vs['bot'], vs['value'])

def main():
	from rules import Rules
	rules = Rules()
	output = []
	from bot import Bot
	bot = [Bot() for i in range(200)];

	# def give():
	# 	try:
	# 		cur_bot.give(vs['value'])
	# 	except Exception, e:
	# 		rule = rules.getruleforbot(vs['bot'])

	# 		if cur_bot.val2 > cur_bot.val1:
	# 			# dest = bot \
	# 				# if rule['high_to_type'] == 'bot' \
	# 				# else output
	# 			if rule['high_to_type'] == 'bot':
	# 				cur_bot = 

	# 		else:
	# 			dest = bot \
	# 				if rule['high_to_type'] == 'bot' \
	# 				else output

	def give(cur_bot, value):
		if cur_bot.give(value) is not None:
			rule = rules.getruleforbot(vs['bot'])
			if cur_bot.too_high:
				if rule['high_to_type'] == 'bot':
					next_bot = bot[rule['high_to']]
					give(next_bot, value)
				else:
					output[rule['high_to']] = value

			else:
				if rule['low_to_type'] == 'bot':
					cur_bot = bot[rule['low_to']]
					give()
				else:
					output[rule['low_to']] = evalue

				

				# dest = bot \
					# if rule['high_to_type'] == 'bot' \
					# else output

			else:
				dest = bot \
					if rule['high_to_type'] == 'bot' \
					else output


				
		

	for line in specification:
		if 'value' in line:
			vs = get_value_spec(line)
			cur_bot = bot[vs['bot']]
			value = vs['value']
			give(cur_bot, value)

			# print str(get_value_spec(line))
			# print value_spec_toString(get_value_spec(line))
		elif 'gives' in line:
			rules.append(get_gives_spec(line))
			# get_gives_spec(line)
			# print str(get_gives_spec(line))

if __name__ == '__main__':
	main()
#