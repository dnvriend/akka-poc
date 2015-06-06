# akka-poc
On 5-5-2015 [Niko Will](https://github.com/n1ko-w1ll) tweeted:

> #SpringBoot won against #akka in my #microservice and #REST comparison. 10.000 concurrent users produced "Connection refused" with akka :(
-- <quote>[Twitter](https://twitter.com/n1ko_w1ll/status/595603289103847424)</quote>

Of course, I am very suprised, because Spray does such a good job of handing a whole lot of requests/sec but I have no production experience with akka-http as of yet, so I forked his repo, adapted it to sbt/scala, (it does not do exactly the same) but the basic gist is it must handle 10.000 users/20 seconds, for a duration of 1 minute. Well, my Macbook Core duo can handle 1000, and then the OSX kernel cannot take it. I will retry on the Macbook Pro asap. 

I will create a branch to test with the Spray libraries.