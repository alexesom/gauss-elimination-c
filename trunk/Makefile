all:
	@gcc -Wall --pedantic -g src/*.c -o bin/gauss -lm

test: all
	@echo "Test 1 poprawne dane 3x3:"
	@bin/gauss dane/A1 dane/b1
	@echo "Test 2 poprawne dane 7x7:"
	@bin/gauss dane/A2 dane/b2
	@echo "Test 3 poprawne dane 5x5 (a11 = 0):"
	@bin/gauss dane/A3 dane/b3
	@echo "Test 4 poprawne dane 6x6 (a11 = 0):"
	@bin/gauss dane/A4 dane/b4
	@echo "Test 5 nieprawidłowe rozmiary macierzy (4x7):"
	@bin/gauss dane/A5 dane/b5
	@echo "Test 6 nieprawidłowe dane:"
	@bin/gauss dane/A6 dane/b6
	@echo "Test 7 macierz osobliwa:"
	@bin/gauss dane/A7 dane/b7
