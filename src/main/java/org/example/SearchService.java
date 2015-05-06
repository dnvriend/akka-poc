package org.example;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import scala.concurrent.duration.Duration;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.model.ContentType;
import akka.http.javadsl.model.HttpCharsets;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.MediaTypes;
import akka.http.javadsl.server.HttpApp;
import akka.http.javadsl.server.RequestContext;
import akka.http.javadsl.server.Route;
import akka.http.javadsl.server.RouteResult;
import akka.japi.Util;
import akka.japi.pf.ReceiveBuilder;
import akka.pattern.Patterns;
import akka.util.Timeout;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class SearchService extends HttpApp
{
   public static void main(String... args)
   {
      final Config config = ConfigFactory.load("example");
      final ActorSystem system = ActorSystem.create("SearchService", config);
      new SearchService(system).bindRoute("localhost", 8080, system);
   }

   private final ActorSystem system;

   public SearchService(final ActorSystem system)
   {
      this.system = system;
   }

   @Override
   public Route createRoute()
   {
      return route(pathPrefix("cr", "1").route(search()));
   }

   private Route search()
   {
      return path("search").route(get(handleWith((ctx) -> handlePerRequest(ctx, new Message()))));
   }

   private RouteResult handlePerRequest(final RequestContext ctx, final Message msg)
   {
      final ActorRef actor = system.actorOf(PerRequestActor.props(ctx));
      final Timeout timeout = new Timeout(Duration.create(2, TimeUnit.SECONDS));
      return ctx.completeWith(Patterns.ask(actor, msg, timeout).mapTo(Util.classTag(RouteResult.class)));
   }

   static class PerRequestActor extends AbstractActor
   {
      static final ContentType CONTENT_TYPE_JSON = ContentType.create(MediaTypes.APPLICATION_JSON, HttpCharsets.UTF_8);

      private final RequestContext ctx;
      private ActorRef requestSender;

      public static Props props(RequestContext ctx)
      {
         return Props.create(PerRequestActor.class, ctx);
      }

      public PerRequestActor(final RequestContext ctx)
      {
         this.ctx = ctx;
         receive(ReceiveBuilder.match(Message.class, msg -> respondStatic(sender())).matchAny(m -> unhandled(m))
            .build());
      }

      private void respondStatic(final ActorRef requestSender)
      {
         this.requestSender = requestSender;
         String jsonArray =
            StreamSupport.stream(Arrays.asList("value1", "value2").spliterator(), false).collect(
               Collectors.joining(",", "[", "]"));
         final HttpResponse response = HttpResponse.create().withEntity(CONTENT_TYPE_JSON, jsonArray);
         completeWithResult(response);
      }

      private <T> void completeWithResult(final HttpResponse response)
      {
         requestSender.tell(ctx.complete(response), null);
         context().stop(self());
      }
   }

   class Message implements Serializable
   {
      private static final long serialVersionUID = 1L;
   }
}
