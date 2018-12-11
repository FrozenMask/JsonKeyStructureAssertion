# JsonKeyStructureAssertion
Json Response Assertion plugin for Jmeter

What can I do:
1.  I am a Jmeter plugin for HttpResponse in Json format;
2.  It is recommended to use Json string with the keys unchanged;
3.  When any key changed, the assertion will fail, I don't care about the value changing.

How to work with me:
1.  Use the pom file to package me to a jar file with maven;
2.  Copy the jar file and fastjson-*.jar to %JMETER_HOME%/lib/ext/ path;
3.  Restart jmeter to load this jar, then add a "JSON Key Sturcture Assertion" under a Http Sampler.
4.  Try me.
