from re import match

specification = []
with open('./test') as testfile:
	for line in testfile:
		specification.append(line)

# first_line = specification[0]

def get_value_spec(line):
	m = match('value ([0-9]+) goes to bot ([0-9]+)', line)
	value_spec = {
		'bot': m.group(2),
		'value': m.group(1)
	}
	return value_spec

def value_spec_toString(vs):
	return "bot[%s].give(%s);" % (vs['bot'], vs['value'])

def get_gives_spec(line):
	m = match('bot ([0-9]+) gives low to (bot|output) ([0-9]+) and high to (bot|output) ([0-9]+)', line)
	# for i in range(1, 6):
		# print(m.group(i),)
	gives_spec = {
		'bot_gives': m.group(1),
		'low_to_type': m.group(2),
		'low_to': m.group(3),
		'high_to_type': m.group(4),
		'high_to': m.group(5)
	}
	return gives_spec

def gives_spec_toString(vs):
	return "bot[%s] = %s;" % (vs['bot'], vs['value'])

for line in specification:
	if 'value' in line:
		# print str(get_value_spec(line))
		print value_spec_toString(get_value_spec(line))
	elif 'gives' in line:
		# get_gives_spec(line)
		print str(get_gives_spec(line))


#