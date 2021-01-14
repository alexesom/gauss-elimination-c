all:
	gcc -Wall --pedantic -g src/*.c -o bin/gauss -lm

test: all
	bin/gauss dane/A dane/b
