# akka-poc
On 2015-05-05 [Niko Will](https://github.com/n1ko-w1ll) [Tweeted](https://twitter.com/n1ko_w1ll/status/595603289103847424) that
`#SpringBoot won against #akka in my #microservice and #REST comparison. 10.000 concurrent users produced "Connection refused" with akka :(`
 
Of course, I am very suprised, because Spray does such a good job of handing a whole lot of requests/sec but I have no production experience with akka-http as of yet, so I forked his repo, adapted it to sbt/scala, (it does not do exactly the same) but the basic gist is it must handle 10.000 users/20 seconds, for a duration of 1 minute. Well, my Macbook Core duo can handle 1000, and then the OSX kernel cannot take it. I will retry on the Macbook Pro asap. 

I will create a branch to test with the Spray libraries.

# Update 2015-06-06
After some tests and a bit of [googling](http://javarevisited.blogspot.nl/2013/08/how-to-fix-javanetsocketexception-too-many-open-files-java-tomcat-weblogic.html)
searching for the problem, it seems that socket connections are treated like files and they use file descriptor, which 
is a limited resource; so testing for 10.000 concurrent users can be problematic. For example, a `ulimit -a` on my OSX systems
shows that the operating system can handle 2560 open files, so the problem lies not with Spray or Akka-HTTP.
 
Actually, after trying to get SpringBoot to work with Scala (I really don't like Java anymore), it seems it has the
same problem as akka-http and Spray... running out of open files.

# Update 2015-06-07
[Gatling](http://gatling.io) has [some tips to tune the operating system](http://gatling.io/docs/2.1.6/general/operations.html)to support handing heavy loads. The `tune.sh` script uses some tweaks for OSX to run the test. It is used by the `test.sh` script to configure the current shell session. Still, after some tweaks and choosing a longer period in which to ramp up users, the desktop won't handle more than 1200 concurrent users (which imho is quite good), but still is far from the 10.000 concurrent users.