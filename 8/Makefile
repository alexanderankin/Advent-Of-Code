MC=Entry
TR=TestRunner

all: run

run: compile
	java -cp bin $(MC)

test: compile
	java -cp bin $(TR)

ensure_bin:
	mkdir -p bin

compile: ensure_bin
	javac *.java -d bin

