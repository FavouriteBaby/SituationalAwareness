import collections
class ExtractAttack:
	def __init__(self):
		self.content = ''

	def readFile(self, filename):
		with open(filename, 'r') as f:
			index = 0
			for line in f.readlines():
				print(line)
				print(len(line))
				if len(line) < 50:
					continue
				elif line[1] == 'I':
					continue
				index = index + 1
				self.dealLine(line, index)
			self.writeLine('test.txt', self.content)

	def dealLine(self, line, index):
		strline = ' '.join(line.split())
		word = strline.split(' ')
		attackName = word[3][15:]
		self.fillBuffer(word[0], attackName, index)
		
	def fillBuffer(self, time, attackName, index):
		if self.content == '':
			self.content = '%s %s %s' % (time, attackName, '\n')
		else:
			self.content = '%s %s %s %s' % (self.content, time, attackName, '\n')
		if index%50 == 0:
			self.writeLine('test.txt', self.content)
			self.content = ''

	def writeLine(self, filename, content):
		with open(filename, 'a+') as f:
			f.write(content)

	def adjustLine(self, filename):
		self.content = ''
		with open(filename, 'r') as f:
			for line in f.readlines():
				if line[0] == ' ':
					line = line[1:]
				if self.content == '':
					self.content = '%s' % (line)
				else:
					self.content = '%s%s' % (self.content, line)
		self.writeLine('adjust.txt', self.content)


	def readAttack(self, filename):
		with open(filename, 'r') as f:
			self.content = ''
			for line in f.readlines():
				attack = line.split(' ')
				attackTime = attack[0][:5]
				if self.content == '':
					self.content = '%s,%s\n' % (attackTime, attack[1])
				else:
					self.content = '%s%s,%s\n' % (self.content, attackTime, attack[1])
			self.writeLine('readAttack.txt', self.content)

	def totalAttack(self, filename):
		time = collections.OrderedDict()
		with open(filename, 'r') as f:
			for line in f.readlines():
				detail = line[:-1].split(',')
				self.fillDDI(time, detail[0], detail[1])
		print(time)

		self.content = ''
		for key in time.keys():
			if self.content == '':
				self.content = '%s' % (key)
			else:
				self.content = '%s%s' % (self.content, key)
			for attack in time[key]:
				self.content = '%s,%s,%s' % (self.content, attack, time[key][attack])
			self.content = '%s\n' % (self.content)
		self.writeLine('totalAttack.txt', self.content)


	def fillDDI(self, dic, oKey, iKey):
		if not dic.has_key(oKey):
			dic[oKey] = {}

		if dic[oKey].has_key(iKey):
			dic[oKey][iKey] = dic[oKey][iKey] + 1
		else:
			dic[oKey][iKey] = 1


if __name__ == '__main__':
	objExAttack = ExtractAttack()
	#objExAttack.readFile('attack.txt')
	#objExAttack.adjustLine('test.txt')
	#objExAttack.readAttack('adjust.txt')
	objExAttack.totalAttack('readAttack.txt')