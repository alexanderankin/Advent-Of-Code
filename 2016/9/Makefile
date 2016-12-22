CFLAGS += -g

solution : solution.c count_string.o

count_string.o : count_string.c

count_string_test : count_string.o count_string_test.c

run_tests : count_string_test
	./count_string_test

clean:
	rm solution count_string count_string_test *.o

