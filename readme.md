# Nbp Currency Exchange Information

Tool made with the purpose of retrieve information of given by the nbp (Polish national bank) of PLN currency

# How does it work?

The program retrieves and transform the information exposed in the public files of the nbp.

In this link we can access to the names of the files of the current year.
http://www.nbp.pl/kursy/xml/dir.txt

If we want to access to files of previous years...
e.i: http://www.nbp.pl/kursy/xml/dir2018.txt

Now we only need to take one of the previous file and open it in this format i.e http://www.nbp.pl/kursy/xml/a001z190102.xml

The name gives information about the type of the file contained, where:

- x: the first letter determines the type of file
  - a the table with average rate of foreign currencies
  - b the table with average rate of non-exchangable currencies
  - c the table with buy and sell rates
  - h the table with exchange units

- z: common letter for every file
- yymmdd: date of issue




