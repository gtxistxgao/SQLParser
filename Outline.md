# SQLParser
## Overview

The purpose of this SQL Parser is to parse the input SQL query then generate the abstract syntax tree (AST). We can write by Javacc, which is an open source parser generator and lexical analyzer generator written in the Java programming language. Till now, the parser can support the simple SQL as follow:

```
select ...
from ...
where ...
```

Besides, the boolean relations in "where" part will only be "and". The output will be a abstract syntax tree (AST) as "AST.xml". 

### Work flow

The work should be down by the following flow:

```
program jj file -> program Main.java -> compile .jj file -> compile .java files
```
Then we can use the Parser. In the following sections, I will explain how to design the .jj file and how to use Main.java to CALL the method in the Parser class. First I will show a demo example of the program, then I will explain how to program the parser.

## Example

```
select e.Name, d.MName 
from Emp e, Dept d 
where e.Name = "Jonny" and (d.Name = c.MName and c.Salary > 7000) and d.Name = "James" and c.Name = d.Name
```

the output will be

```
<dbQuery><dbSFWStatement><dbSelectClause> ... </dbWhereClause></dbSFWStatement></dbQuery>
```

the original output is not formatted but it's fine since it is an XML file. The formatted XML output will be:

```
<dbQuery>
    <dbSFWStatement>
        <dbSelectClause>
            <dbAttr>
                <dbRelVar>
                    <dbRelAliasName Token="e" />
                </dbRelVar>
                <dbAttrName Token="Name" />
            </dbAttr>
            <dbAttr>
                <dbRelVar>
                    <dbRelAliasName Token="d" />
                </dbRelVar>
                <dbAttrName Token="MName" />
            </dbAttr>
        </dbSelectClause>
        <dbFromClause>
            <dbRelVar>
                <dbRelName Token="Emp" />
                <dbRelAliasName Token="e" />
            </dbRelVar>
            <dbRelVar>
                <dbRelName Token="Dept" />
                <dbRelAliasName Token="d" />
            </dbRelVar>
        </dbFromClause>
        <dbWhereClause>
            <BooleanExp>
                <BooleanFactor>
                    <dbAttr>
                        <dbRelVar>
                            <dbRelAliasName Token="e" />
                        </dbRelVar>
                        <dbAttrName Token="Name" />
                    </dbAttr>
                    <comparisonOp Token="=" />
                    <dbConstValue>
                        <STRINGLITERAL Token="Jonny"/>
                    </dbConstValue>
                </BooleanFactor>
                <BooleanExp>
                    <BooleanExp>
                        <BooleanFactor>
                            <dbAttr>
                                <dbRelVar>
                                    <dbRelAliasName Token="d" />
                                </dbRelVar>
                                <dbAttrName Token="Name" />
                            </dbAttr>
                            <comparisonOp Token="=" />
                            <dbAttr>
                                <dbRelVar>
                                    <dbRelAliasName Token="c" />
                                </dbRelVar>
                                <dbAttrName Token="MName" />
                            </dbAttr>
                        </BooleanFactor>
                        <BooleanFactor>
                            <dbAttr>
                                <dbRelVar>
                                    <dbRelAliasName Token="c" />
                                </dbRelVar>
                                <dbAttrName Token="Salary" />
                            </dbAttr>
                            <comparisonOp Token=">" />
                            <dbConstValue>
                                <INTEGERLITERAL Token="7000"/>
                            </dbConstValue>
                        </BooleanFactor>
                    </BooleanExp>
                    <BooleanExp>
                        <BooleanFactor>
                            <dbAttr>
                                <dbRelVar>
                                    <dbRelAliasName Token="d" />
                                </dbRelVar>
                                <dbAttrName Token="Name" />
                            </dbAttr>
                            <comparisonOp Token="=" />
                            <dbConstValue>
                                <STRINGLITERAL Token="James"/>
                            </dbConstValue>
                        </BooleanFactor>
                        <BooleanFactor>
                            <dbAttr>
                                <dbRelVar>
                                    <dbRelAliasName Token="c" />
                                </dbRelVar>
                                <dbAttrName Token="Name" />
                            </dbAttr>
                            <comparisonOp Token="=" />
                            <dbAttr>
                                <dbRelVar>
                                    <dbRelAliasName Token="d" />
                                </dbRelVar>
                                <dbAttrName Token="Name" />
                            </dbAttr>
                        </BooleanFactor>
                    </BooleanExp>
                </BooleanExp>
            </BooleanExp>
        </dbWhereClause>
    </dbSFWStatement>
</dbQuery>
```

## Example Explanation
Firstly, this SQL query will not check if the alias name of table in the "where" clause have it's corresponding alias name in "from" clause. Secondly, this SQL query example has the most situation. Especially in the where part, situations are more complicated than "select" and "from" part. In "where" clause, we will have "STRINGLITERAL", "INTEGERLITERAL" and relation.attribute. 

In the example, the "where" part is:
```
where e.Name = "Jonny" and (d.Name = c.MName and c.Salary > 7000) and d.Name = "James" and c.Name = d.Name
```
We can The corresponding abstract syntax tree should corresponding to the same structure of this query, which is shown as below:
```
<dbWhereClause>
	<BooleanExp>
    	<BooleanFactor>	------------------> e.Name = "Jonny"
        <BooleanExp>
            <BooleanExp>
                <BooleanFactor>	----------> d.Name = c.MName
                <BooleanFactor> ----------> c.Salary > 7000
        	</BooleanExp>
            <BooleanExp>
                <BooleanFactor> ----------> d.Name = "James"
                <BooleanFactor> ----------> c.Name = d.Name
            </BooleanExp>
        </BooleanExp>
    </BooleanExp>
</dbWhereClause>
```

## JavaCC Grammar Explanation
In JavaCC, we have two method to implement this parser. First one is to use jjtree, which is a preprocessor for JavaCC that inserts parse tree building actions at various places in the JavaCC source. The output of JJTree is run through JavaCC to create the parser. However, in this project, I directly use the second option, .jj file to implement it. The grammar are explained as following.

### JavaCC options setting up

First, we need to set the options as what we need. In this case, ignore the case and make the method static.

```
options
{
  IGNORE_CASE = true;
  STATIC = true;
}
```

### Parse Method programming

Then, write the parse method and start to parse when call this method. This method will return the AST based on the query we get.

```
PARSER_BEGIN(Parser)
public class Parser
{
  public static String parse(String args) throws Exception
  {
    Parser parse = new Parser(new java.io.StringReader(args));
    String rst = parse.Query();
    return rst;
  }
}

PARSER_END(Parser)
```
### Setup Tokens

Next step is to setup the tokens of this parser.
Because there may have some token in the input query that not related with parse, so we SKIP tokens of " ", "\t", "\r" and "\n"

#### Skip some tokens

```
SKIP : { " " | "\t" | "\r" | "\n" }
```

#### Normal Token Setup

parse the tokens of SELECT, FROM, WHERE and AND

```
TOKEN :
{
  < SELECT : "SELECT" >
| < FROM : "FROM" >
| < WHERE : "WHERE" >
| < AND : "AND" >
}
```
#### Use Regular Expression
We can set up other tokens by the similar method. Besides, there are two token used regular expression shown as follow:

```
TOKEN :
{
  < DIGITS : ([ "0"-"9" ])+ >
}
```
this token will represent all the mix of table name or attribute name
```
TOKEN :
{
  < NAME : ([ "a"-"z", "0"-"9" ])+ >
}

### From an XML Structure

Next is to write the grammar to build up the XML structure. As we know, XML documents form a tree structure that starts at "the root" and branches to "the leaves". 

```
<root>
	<child>
		<subchild>.....</subchild>
	</child>
</root>
```
Every level in xml need a start tag and a close tag. For example, the root has a start tag ```<root>``` and a close tag ```</root>```. 

So in this project, the idea is that we can construct our xml file by add a start tag and close tag. 

For example, in starting the query, call SFWStatement() and add ```"<dbQuery>"``` and ```"</dbQuery>"``` out of the SFWStatement.
```
String Query() :
{
  String rst;
}
{
  rst = SFWStatement() < EOF >
  {
    return "<dbQuery>" + rst + "</dbQuery>";
  }
}
```
Then we can check three clause separately. Then do the similar operation, add ```<dbSFWStatement>``` and ```</dbSFWStatement>``` in the beginning and ending.

```
String SFWStatement() :
{
  String select = "";
  String from = "";
  String where = "";
}
{
  select = SelectClause() from = FromClause() where = WhereClause()
  {
    return "<dbSFWStatement>" + select + from + where + "</dbSFWStatement>";
  }
}
```
### How to find multiple relations

For example, this is the select clause, we need to find all the attribute so we call Attr() method

```
String SelectClause() :
{
  String select;
}
{
  < SELECT > select = Attr()
  {
    return "<dbSelectClause>" + select + "</dbSelectClause>";
  }
}
```
Then in the Attr(), we can see there is a regular expression ```(< COMMA > subAttr = Attr())*```. It will repeatedly check if there is more attributes next, because if there is one, we will see a comma.

```
String Attr() :
{
  Token relation;
  Token attr;
  String subAttr = "";
}
{
  relation = < NAME > < DOT > attr = < NAME >
  (
    < COMMA > subAttr = Attr()
  )*
  {
    return "<dbAttr>" + "<dbRelVar> <dbRelAliasName Token=\"" + relation.image + "\" /> </dbRelVar>" + "<dbAttrName Token=\"" + attr.image + "\" /> " + "</dbAttr>" + subAttr;
  }
}
```

## JJ File Conclusion

So based on the ideas explained above, we can program a .jj file to Parse XML Query. For more details of how to implement jj file, please see the README file.

## Compile Parser

Go to your jj file's folder. Then type:
```
franktekimbp:MyProgram frankgao$ javacc SQLParser.jj
```
Then we will see:
```
franktekimbp:MyProgram frankgao$ javacc SQLParser.jj
Java Compiler Compiler Version 5.0 (Parser Generator)
(type "javacc" with no arguments for help)
Reading from file SQLParser.jj . . .
File "TokenMgrError.java" does not exist.  Will create one.
File "ParseException.java" does not exist.  Will create one.
File "Token.java" does not exist.  Will create one.
File "SimpleCharStream.java" does not exist.  Will create one.
Parser generated with 0 errors and 0 warnings.
```

Then we compile all the .java files.

```
franktekimbp:MyProgram frankgao$ javac *.java
```
Then the Parser is compiled.

## Use the Parser

So next I'm going to explain how to use the parser.

In this demo, I will use Main.java to show how to use the Parser. We can directly use the Parser.parse(input) to parse the input String. The method will return the AST.

```
private static String parse(String input) {
	String rst = "";
	try {
		rst = Parser.parse(input);
	} catch (Exception e) {
		System.out.println("Error during Parsing");
		e.printStackTrace();
	}
	return rst;
}
```
Then we can do something to the AST String. I will print it out and output it into an AST.xml file. That's it.

## User Guide

1. put “input.txt” and “AST.xml” into Project file Path. For example, in eclipse, out of src and bin folder.
2. if you want to import other files as input. change the inputPath String in the Main.java

	```
	String inputPath = "input.txt";
	```

3. the AST.xml will be generated automatically in the Java Project file folder. So if want the output into other folders, change the outputPath in the Main.java

	```
	String outputPath = "AST.xml";
	```

4. The Main.java will not be generated automatically by javacc. So if you need to use the parser for other purposes, please read the Main.java File and modify the file.

5. email me if have any other questions regarding this project, email is in the Main.java File.

6. If using JavaCC for the first time. The [JavaCC Tutorial](https://github.com/PosFrank/SQLParser/blob/master/javacc-tutorial.pdf) is a very good reference to learn how to use it. After following all the instructions in the tutorial, I believe this project is quite understandable.

Best

Tianxiang

Apr 5 2016
