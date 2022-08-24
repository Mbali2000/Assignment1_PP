#Makefile Assignment 1
#MYZMBA002
#14 August 2022

#JAVAC+/user/bin/javac
.SUFFIXES:	.java	.class
SRCDIR=src
BINDIR=bin
JAVAC=javac

$(BINDIR)/%.class:$(SRCDIR)/%.java
		$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES=MeanFilterSerial.class MeanFilterParallel.class MedianFilterSerial.class MedianFilterParallel.class

CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

clean:
		rm $(BINDIR)/*.class

run1: $(CLASS_FILES)
	java -cp $(BINDIR) MeanFilterSerial "image2.jpg" "output.jpg" "3" 

run2: $(CLASS_FILES)
	java -cp $(BINDIR) MeanFilterParallel "image2.jpg" "output.jpg" "3"

run3: $(CLASS_FILES)
	java -cp $(BINDIR) MedianFilterSerial "image2.jpg" "output.jpg" "3"

run4: $(CLASS_FILES)
	java -cp $(BINDIR) MedianFilterParallel "image2.jpg" "output.jpg" "3"


########################################################################################
# Change input image name and integer value to change image and manipulate window size #