all : run

run : compile
	./bin/Entry
	
compile : ensure_bin Entry.cpp Grid.cpp Grid.h
	g++ *.cpp -o bin/Entry -g

ensure_bin :
	mkdir -p bin
