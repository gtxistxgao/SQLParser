# SQLParser
The purpose of this SQL Parser is to parse the input SQL query then generate the abstract syntax tree (AST). We can write by Javacc, which is an open source parser generator and lexical analyzer generator written in the Java programming language. Till now, the parser can support the simple SQL as follow:

```
select ...
from ...
where ...
```

Besides, the boolean relations in "where" part will only be "and". The output will be a abstract syntax tree (AST) as "AST.xml". An example is shown as below:

#Example

```
select e.Name, d.MName 
from Emp e, Dept d 
where e.Name = "Jonny" and (d.Name = c.MName and c.Salary > 7000) and d.Name = "James" and c.Name = d.Name
```

the output will be

```
<dbQuery><dbSFWStatement><dbSelectClause><dbAttr><dbRelVar> <dbRelAliasName Token="e" /> </dbRelVar><dbAttrName Token="Name" /> </dbAttr><dbAttr><dbRelVar> <dbRelAliasName Token="d" /> </dbRelVar><dbAttrName Token="MName" /> </dbAttr></dbSelectClause><dbFromClause><dbRelVar><dbRelName Token="Emp" /><dbRelAliasName Token="e" /> </dbRelVar><dbRelVar><dbRelName Token="Dept" /><dbRelAliasName Token="d" /> </dbRelVar></dbFromClause><dbWhereClause><BooleanExp><BooleanFactor><dbAttr><dbRelVar> <dbRelAliasName Token="e" /> </dbRelVar><dbAttrName Token="Name" /> </dbAttr><comparisonOp Token="=" /><dbConstValue><STRINGLITERAL Token="Jonny"/> </dbConstValue></BooleanFactor><BooleanExp><BooleanExp><BooleanFactor><dbAttr><dbRelVar> <dbRelAliasName Token="d" /> </dbRelVar><dbAttrName Token="Name" /> </dbAttr><comparisonOp Token="=" /><dbAttr><dbRelVar> <dbRelAliasName Token="c" /> </dbRelVar><dbAttrName Token="MName" /> </dbAttr></BooleanFactor><BooleanFactor><dbAttr><dbRelVar> <dbRelAliasName Token="c" /> </dbRelVar><dbAttrName Token="Salary" /> </dbAttr><comparisonOp Token=">" /><dbConstValue> <INTEGERLITERAL Token="7000"/> </dbConstValue></BooleanFactor></BooleanExp><BooleanExp><BooleanFactor><dbAttr><dbRelVar> <dbRelAliasName Token="d" /> </dbRelVar><dbAttrName Token="Name" /> </dbAttr><comparisonOp Token="=" /><dbConstValue><STRINGLITERAL Token="James"/> </dbConstValue></BooleanFactor><BooleanFactor><dbAttr><dbRelVar> <dbRelAliasName Token="c" /> </dbRelVar><dbAttrName Token="Name" /> </dbAttr><comparisonOp Token="=" /><dbAttr><dbRelVar> <dbRelAliasName Token="d" /> </dbRelVar><dbAttrName Token="Name" /> </dbAttr></BooleanFactor></BooleanExp></BooleanExp></BooleanExp></dbWhereClause></dbSFWStatement></dbQuery>
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

##Example Explanation
Firstly, this SQL query will not check if the alias name of table in the "where" clause have it's corresponding alias name in "from" clause. Secondly, this SQL query example has the most situation. Especially in the where part, situations are more complicated than "select" and "from" part. In "where" clause, we will have "STRINGLITERAL", "INTEGERLITERAL" and relation.attribute. In the example, the "where" part is 

```
where e.Name = "Jonny" and (d.Name = c.MName and c.Salary > 7000) and d.Name = "James" and c.Name = d.Name
```
The corresponding abstract syntax tree should corresponding to the same structure of this query, which is shown as below:
```
<dbWhereClause>
	<BooleanExp>
    <BooleanFactor>
        <BooleanExp>
            <BooleanExp>
                <BooleanFactor>
                <BooleanFactor>
        	</BooleanExp>
            <BooleanExp>
                <BooleanFactor>
                <BooleanFactor>
            </BooleanExp>
        </BooleanExp>
    </BooleanExp>
</dbWhereClause>
```

#JavaCC Grammar Explanation
In JavaCC, we have two method to implement this parser. First one is to use jjtree, which is a preprocessor for JavaCC that inserts parse tree building actions at various places in the JavaCC source. The output of JJTree is run through JavaCC to create the parser. However, in this project, I directly use the second option, .jj file to implement it. The grammar are explained as following.

First, we need to set the options as what we need. In this case, ignore the case and make the method static.

```
options
{
  IGNORE_CASE = true;
  STATIC = true;
}
```

Then, initialize the parse method and start to parse when call this method. This method will return the AST based on the query we get.

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

Next step is to setup the tokens of this parser.
Because there may have some token in the input query that not related with parse, so we SKIP tokens of " ", "\t", "\r" and "\n"

```
SKIP :
{
  " "
| "\t"
| "\r"
| "\n"
}
```

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

In case of the query has "(" and ")", there should have token as ```OPEN_PAR``` and ```CLOSE_PAR``` token

```
TOKEN :
{
  < OPEN_PAR : "(" >
}

TOKEN :
{
  < CLOSE_PAR : ")" >
}
```

dot and comma are also need to be setup as token for the query relation.attribute and multiply boolean factor.

```
TOKEN :
{
  < DOT : "." >
}

TOKEN :
{
  < COMMA : "," >
}
```

In case that quotation mark " will be used in the where part when it's a STRINGLITERAL

```
TOKEN :
{
  < QUO : "\"" >
}
```

because operator has no difference in parse query into AST. So we set them all as OPERATOR

```
TOKEN :
{
  < OPERATOR :
    ">"
  | "< "
  | "="
  | ">="
  | "<="
  | "<>"
  | "!=" >
}
```

this token represent only the numbers. it will be used in the where clause to recognize the INTEGERLITERAL

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
```

start the query, call SFWStatement() and add "<dbQuery>""</dbQuery>" out of the SFWStatement.

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

first check the select clause
then check the from clause
finally check the where clause

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

this is the select clause, we need to find all the attribute so we call Attr method

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

this method will recursively find all the attribute in the select clause and return all the attribute as one String.


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
The regular expression ```(< COMMA > subAttr = Attr())*``` will recursively all it self to find all the attributes. The warning is fine in this command because javacc prefer us to use LOOKAHEAD, but our way is also fine.

Next is the from clause, we will call RelVal() method to find all relations

```
String FromClause() :
{
  String from;
}
{
  < FROM > from = RelVal()
  {
    return "<dbFromClause>" + from + "</dbFromClause>";
  }
}
```

the RelVal() method will find all the relations in the from clause. The regular expression ```(< COMMA > subVal = RelVal())*```will recursively all this method to find all the reaName and aliasName in the from clause.

```
String RelVal() :
{
  Token realName;
  Token aliasName;
  String subVal = "";
}
{
  realName = < NAME > aliasName = < NAME >
  (
    < COMMA > subVal = RelVal()
  )*
  {
    return "<dbRelVar>" + "<dbRelName Token=\"" + realName.image + "\" />" + "<dbRelAliasName Token=\"" + aliasName.image + "\" /> </dbRelVar>" + subVal;
  }
}
```

Next is the where clause. It will have several recursion next in this method.
First we call Expression and assign false to represent that this is the first time we call it, So it must add <BooleanExp><\BooleanExp> out of the where clause abstract syntex tree

```
String WhereClause() :
{
  String where = "";
}
{
  < WHERE > where = Expression(false)
  {
    return "<dbWhereClause>" + where + "</dbWhereClause>";
  }
}
```

The Expression() method will find all the boolean factors and add BooleanExp in the case we have more than two boolean factors or has parenthesis in some of the boolean factors. In each call of this method, it will firstly check if there is parenthesis in this boolean expression, if true, we take the first boolean factor and check if there are parenthesis in this parenthesis and pass a true value to the next recursion in the case when we have"((...))" in the query. If hasFather == true means we have the <BooleanExp></BooleanExp> out of this time's call. Then we check if exp2 is empty. if exp2 is empty after the parsing. This means this time is the final BooleanFactor in this BooleanExpression and we don't want to have a single BooleanFactor in the BooleanExp, so we will not add "<BooleanExp>"..."</BooleanExp>" out of it. If it has no father parenthesis out of it or exp2 not empty, we need to add the BooleanExp out of exp1 + exp2. Next, if the first token is not parenthesis, we need to check if this satisfy the boolean factor rules. So we call Factor() method to parse it. If the first factor is good, we need to check the next one. 
```(< AND > exp2 = Expression(true))*``` can be a boolean factor or a parenthesis, so we call Expression() method again.

```
String Expression(boolean hasFather) :
{
  String exp1 = "";
  String exp2 = "";
}
{
  < OPEN_PAR > exp1 = Expression(true) < CLOSE_PAR >
  (
    < AND > exp2 = Expression(true)
  )*
  {
    if (hasFather == true && exp2.equals(""))
    {
      return exp1;
    }
    return "<BooleanExp>" + exp1 + exp2 + "</BooleanExp>";
  }
| exp1 = Factor()
  (
    < AND > exp2 = Expression(true)
  )*
  {
    /*the next check has the same function as the previous checking*/
    if (hasFather == true && exp2.equals(""))
    {
      return exp1;
    }
    else
    {
      return "<BooleanExp>" + exp1 + exp2 + "</BooleanExp>";
    }
  }
}
```

the Factor() method will parse the Boolean factor. It can be three situation.
1. it is a table.Attribute. such as "Emp.Name = Dep.MName"
2. it is a string literal. such as "Emp.Name = "james""
3. it is a integer literal. such as "Emp.Salary = 7000"
So we can find all three situation has a left part and and an operation and a right part. 

```
String Factor() :
{
  String left = "";
  String right = "";
  String operator;
}
{
  /*so we parse the left, right and operator one by one*/
  left = BooleanAttr()
  operator = Operator()
  right = BooleanAttr()
  {
    return "<BooleanFactor>" + left + operator + right + "</BooleanFactor>";
  }
}
```

the BooleanAttr() method can have three situations as well.
1. name.name such as "Emp.Name"
2. String Literal such as "James"
3. Integer Literal such as 7000

so this method will check them and return the corresponding attribute

```
String BooleanAttr() :
{
  Token rel;
  Token attr;
  String attrName = "";
}
{
  rel = < DIGITS >
  {
    return "<dbConstValue> <INTEGERLITERAL Token=\"" + rel.image + "\"/> </dbConstValue>";
  }
| rel = < NAME > < DOT > attr = < NAME >
  {
    return "<dbAttr>" + "<dbRelVar> <dbRelAliasName Token=\"" + rel.image + "\" /> </dbRelVar>" + "<dbAttrName Token=\"" + attr.image + "\" /> </dbAttr>";
  }
| < QUO > rel = < NAME > < QUO >
  {
    return "<dbConstValue><STRINGLITERAL Token=\"" + rel.image + "\"/> </dbConstValue>";
  }
}
```

Finally, the operator() method will check the operator and return it as what it is.

```
String Operator() :
{
  Token operator;
}
{
  operator = < OPERATOR >
  {
    return "<comparisonOp Token=\"" + operator.image + "\" />";
  }
}
```

#User Guide

1. put “input.txt” and “AST.xml” into Project file Path. For example, in eclipse, out of src and bin folder.
2. if you want to input other files of input. change the inputPath String in the Main.java

	```
	String inputPath = "input.txt";
	```

3. the AST.xml will be generated automatically in the Java Project file folder. So if want the output in other folders, change the outputPath in the Main.java

	```
	String outputPath = "AST.xml";
	```

4. The Main.java will not be generated automatically by javacc. So if you need to use the parser for aother purpose, please read the Main.java File to modify the file as the way you need.

5. email me if have any other questions regarding this project, email is in the Main.java File.

6. If using JavaCC for the first time. The[JavaCC Tutorial](https://github.com/PosFrank/SQLParser/blob/master/javacc-tutorial.pdf) is a very good reference to learn how to use it. After following all the instructions in the tutorial. I believe it is understandable on this project.

Thanks

Tianxiang

Apr 5 2016