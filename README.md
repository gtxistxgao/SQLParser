# SQLParser

Till now, the SQLParser support the parse of 

```
select ...
from ...
where
```

the output will be a abstract syntax tree (AST) as AST.xml

#Example

```
select e.Name, d.MName 
from Emp e, Dept d 
where d.Name = c.MName and (((c.Salary > 7000 and d.Name = "James" and c.Name = d.Name)))
```

the output will be

```
<dbQuery><dbSFWStatement><dbSelectClause><dbAttr><dbRelVar> <dbRelAliasName Token="e" /> </dbRelVar><dbAttrName Token="Name" /> </dbAttr><dbAttr><dbRelVar> <dbRelAliasName Token="d" /> </dbRelVar><dbAttrName Token="MName" /> </dbAttr></dbSelectClause><dbFromClause><dbRelVar><dbRelName Token="Emp" /><dbRelAliasName Token="e" /> </dbRelVar><dbRelVar><dbRelName Token="Dept" /><dbRelAliasName Token="d" /> </dbRelVar></dbFromClause><dbWhereClause><BooleanExp><BooleanFactor><dbAttr><dbRelVar> <dbRelAliasName Token="d" /> </dbRelVar><dbAttrName Token="Name" /> </dbAttr><comparisonOp Token="=" /><dbAttr><dbRelVar> <dbRelAliasName Token="c" /> </dbRelVar><dbAttrName Token="MName" /> </dbAttr></BooleanFactor><BooleanExp><BooleanFactor><dbAttr><dbRelVar> <dbRelAliasName Token="c" /> </dbRelVar><dbAttrName Token="Salary" /> </dbAttr><comparisonOp Token=">" /><dbConstValue> <INTEGERLITERAL Token="7000"/> </dbConstValue></BooleanFactor><BooleanExp><BooleanFactor><dbAttr><dbRelVar> <dbRelAliasName Token="d" /> </dbRelVar><dbAttrName Token="Name" /> </dbAttr><comparisonOp Token="=" /><dbConstValue><STRINGLITERAL Token="James"/> </dbConstValue></BooleanFactor><BooleanFactor><dbAttr><dbRelVar> <dbRelAliasName Token="c" /> </dbRelVar><dbAttrName Token="Name" /> </dbAttr><comparisonOp Token="=" /><dbAttr><dbRelVar> <dbRelAliasName Token="d" /> </dbRelVar><dbAttrName Token="Name" /> </dbAttr></BooleanFactor></BooleanExp></BooleanExp></BooleanExp></dbWhereClause></dbSFWStatement></dbQuery>
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
                <BooleanExp>
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

#Outline:

1. put “input.txt” and “AST.xml” into Project file Path
1. if you want to input other files of input. change the inputPath String in the Main.java

```
String inputPath = "input.txt";
```

1. the AST.xml will generated automatically in the Java Project file folder. So if want the output in other folder. Change the outputPath in the Main.java

```
String outputPath = "AST.xml";
```

1. The Main.java will not be generated automatically by javacc. So if you need to use the parser for other purpose, please read the Main.java File to modify the file as the way you need.

1. email gtx@iastae.edu if have any other questions regard this project.

Thanks
Tianxiang