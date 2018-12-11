# JsonKeyStructureAssertion
JSON Response Assertion plugin for Jmeter

What can I do:
1.  I am a Jmeter plug-in that handles HTTP response assertions in JSON format.
2.  It is recommended to use when the response JSON with the keys unchanged.
3.  When any key changed, the assertion will fail.
4.  I don't care about any value changing of the JSON.

How to work with me:
1.  Use the pom file to package me to a jar file with maven;
2.  Copy the jar file and fastjson-*.jar to %JMETER_HOME%/lib/ext/ path;
3.  Restart jmeter to load this plug-in, then add a "JSON Key Sturcture Assertion" under a Http Sampler.
4.  Try me.


功能简介：

1. 我是一个Jmeter插件，用来处理JSON格式的Http响应断言。
2. 推荐在JSON的key值不变的场景下使用。
3. 当JSON中的任何一个key发生变化的时候，断言就会失败。
4. 我不关心JSON中的value值变化。

使用简介：
1.  用maven把我打成jar包；
2.  把新打好的jar包以及fastjson.jar一起放到 %JMETER_HOME%/lib/ext/ 目录下；
3.  重启Jmeter以加载该插件，然后就可以在HTTP Sapmpler下面添加"JSON Key Sturcture Assertion"了；
4.  试试吧
