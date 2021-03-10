build:
	javac *.java

run-p1:
	java Bani

run-p2:
	java Gard

run-p3:
	java Bomboane

run-p4:
	java Sala

clean: Bani Gard Bomboane Sala
	rm Bani Gard Bomboane Sala

