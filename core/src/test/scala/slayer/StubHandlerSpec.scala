package slayer

import zio.*
import zio.test.*

/** M3 unit tests: drive the function-handler branch of `stub`.
  *
  * Each test stubs a method with a handler (tupled or curried), then invokes the resulting `Array[Any] => Any` adapter
  * via `callStubbed` and asserts the adapter unrolls correctly. Methods on the test trait cover single-list / curried /
  * tupled-vs-curried-handler / high-arity / generic shapes.
  */
object StubHandlerSpec extends ZIOSpecDefault:

  // ----- Service trait ----------------------------------------------------------------------------------------------

  trait Repo:
    def find(id: Int): String
    def merge(a: Int, b: Int, c: Int): Int
    def composed(a: Int)(b: String)(c: Boolean): String
    def two(a: Int, b: String): String
    def get[A](id: Int): UIO[A]
    def big(
        a1: Int,
        a2: Int,
        a3: Int,
        a4: Int,
        a5: Int,
        a6: Int,
        a7: Int,
        a8: Int,
        a9: Int,
        a10: Int,
        a11: Int,
        a12: Int,
    ): Int
    def wide(
        a1: Int,
        a2: Int,
        a3: Int,
        a4: Int,
        a5: Int,
        a6: Int,
        a7: Int,
        a8: Int,
        a9: Int,
        a10: Int,
        a11: Int,
        a12: Int,
        a13: Int,
        a14: Int,
        a15: Int,
        a16: Int,
        a17: Int,
        a18: Int,
        a19: Int,
        a20: Int,
        a21: Int,
        a22: Int,
        a23: Int,
    ): Int
  end Repo

  final class RepoStubbed extends Stubbed[Repo]

  // ----- Helpers ----------------------------------------------------------------------------------------------------

  private def runStub[A](program: URIO[Stubbed[Repo], A]): UIO[(A, RepoStubbed)] =
    val store = RepoStubbed()
    program.provide(ZLayer.succeed[Stubbed[Repo]](store)).map(a => (a, store))

  def spec = suite("StubHandlerSpec — M3 stub macro, function-handler branch")(
    test("arity 1 — handler parameter is the method's parameter type") {
      for (_, store) <- runStub(stub[Repo](_.find(slayer.any)) { id =>
          val typed: Int = id
          s"id=$typed"
        })
      yield assertTrue(
        store.callStubbed[String](MethodId("find", List("Int")), Array[Any](42)) == "id=42"
      )
    },
    test("arity 2 — handler parameters need no ascription") {
      for (_, store) <- runStub(stub[Repo](_.two(slayer.any, slayer.any)) { (a, b) =>
          val left: Int     = a
          val right: String = b
          s"$left:$right"
        })
      yield assertTrue(
        store.callStubbed[String](MethodId("two", List("Int", "String")), Array[Any](7, "x")) == "7:x"
      )
    },
    test("curried handler parameters need no ascription") {
      for (_, store) <- runStub(stub[Repo](_.composed(slayer.any)(slayer.any)(slayer.any)) { a => b => c =>
          val ia: Int     = a
          val sb: String  = b
          val bc: Boolean = c
          s"$ia/$sb/$bc"
        })
      yield assertTrue(
        store.callStubbed[String](
          MethodId("composed", List("Int", "String", "Boolean")),
          Array[Any](1, "x", true),
        ) == "1/x/true"
      )
    },
    test("arity 1 — tupled handler") {
      for (_, store) <- runStub(stub[Repo](_.find(slayer.any))((id: Int) => s"id=$id"))
      yield assertTrue(
        store.callStubbed[String](MethodId("find", List("Int")), Array[Any](42)) == "id=42"
      )
    },
    test("arity 2 — tupled handler, single param list") {
      for (_, store) <- runStub(stub[Repo](_.two(slayer.any, slayer.any))((a: Int, b: String) => s"$a:$b"))
      yield assertTrue(
        store.callStubbed[String](
          MethodId("two", List("Int", "String")),
          Array[Any](7, "x"),
        ) == "7:x"
      )
    },
    test("arity 3 — tupled handler, single param list") {
      for (_, store) <- runStub(
          stub[Repo](_.merge(slayer.any, slayer.any, slayer.any))((a: Int, b: Int, c: Int) => a + b + c)
        )
      yield assertTrue(
        store.callStubbed[Int](
          MethodId("merge", List("Int", "Int", "Int")),
          Array[Any](1, 2, 3),
        ) == 6
      )
    },
    test("arity 3 — curried handler, curried method") {
      val handler = (a: Int) => (b: String) => (c: Boolean) => s"$a/$b/$c"
      for (_, store) <- runStub(stub[Repo](_.composed(slayer.any)(slayer.any)(slayer.any))(handler))
      yield assertTrue(
        store.callStubbed[String](
          MethodId("composed", List("Int", "String", "Boolean")),
          Array[Any](1, "x", true),
        ) == "1/x/true"
      )
    },
    test("arity 3 — tupled handler, curried method (handler shape ignored)") {
      val handler = (a: Int, b: String, c: Boolean) => s"$a/$b/$c"
      for (_, store) <- runStub(stub[Repo](_.composed(slayer.any)(slayer.any)(slayer.any))(handler))
      yield assertTrue(
        store.callStubbed[String](
          MethodId("composed", List("Int", "String", "Boolean")),
          Array[Any](2, "y", false),
        ) == "2/y/false"
      )
    },
    test("arity 2 — curried handler, single-list method") {
      val handler = (a: Int) => (b: String) => s"$a-$b"
      for (_, store) <- runStub(stub[Repo](_.two(slayer.any, slayer.any))(handler))
      yield assertTrue(
        store.callStubbed[String](
          MethodId("two", List("Int", "String")),
          Array[Any](9, "z"),
        ) == "9-z"
      )
    },
    test("arity 12 — past Function9 / no ceiling") {
      val handler =
        (
            a1: Int,
            a2: Int,
            a3: Int,
            a4: Int,
            a5: Int,
            a6: Int,
            a7: Int,
            a8: Int,
            a9: Int,
            a10: Int,
            a11: Int,
            a12: Int,
        ) => a1 + a2 + a3 + a4 + a5 + a6 + a7 + a8 + a9 + a10 + a11 + a12
      for (_, store) <- runStub(
          stub[Repo](
            _.big(
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
            )
          )(handler)
        )
      yield assertTrue(
        store.callStubbed[Int](
          MethodId("big", List.fill(12)("Int")),
          Array[Any](1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
        ) == 78
      )
    },
    test("arity 23 — curried handler, past Function22") {
      val handler =
        (a1: Int) =>
          (a2: Int) =>
            (a3: Int) =>
              (a4: Int) =>
                (a5: Int) =>
                  (a6: Int) =>
                    (a7: Int) =>
                      (a8: Int) =>
                        (a9: Int) =>
                          (a10: Int) =>
                            (a11: Int) =>
                              (a12: Int) =>
                                (a13: Int) =>
                                  (a14: Int) =>
                                    (a15: Int) =>
                                      (a16: Int) =>
                                        (a17: Int) =>
                                          (a18: Int) =>
                                            (a19: Int) =>
                                              (a20: Int) =>
                                                (a21: Int) =>
                                                  (a22: Int) =>
                                                    (a23: Int) =>
                                                      a1 + a2 + a3 + a4 + a5 + a6 + a7 + a8 + a9 + a10 + a11 + a12 +
                                                        a13 + a14 + a15 + a16 + a17 + a18 + a19 + a20 + a21 + a22 + a23
      for (_, store) <- runStub(
          stub[Repo](
            _.wide(
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
              slayer.any,
            )
          )(handler)
        )
      yield assertTrue(
        store.callStubbed[Int](
          MethodId("wide", List.fill(23)("Int")),
          Array[Any](1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23),
        ) == 276
      )
    },
    test("handler ignores its args") {
      for (_, store) <- runStub(stub[Repo](_.two(slayer.any, slayer.any))((_: Int, _: String) => "fixed"))
      yield assertTrue(
        store.callStubbed[String](MethodId("two", List("Int", "String")), Array[Any](0, "")) == "fixed"
      )
    },
    test("generic method — handler returns a ZIO effect") {
      val handler: Int => UIO[String] = (id: Int) => ZIO.succeed(s"id=$id")
      for
        (_, store) <- runStub(stub[Repo](_.get[String](slayer.any))(handler))
        got        <- store.callStubbed[UIO[String]](MethodId("get", List("Int")), Array[Any](7))
      yield assertTrue(got == "id=7")
    },
    test("compile error: handler return type doesn't match method return") {
      for r <- typeCheck("slayer.stub[slayer.StubHandlerSpec.Repo](_.find(slayer.any))((i: Int) => 42)")
      yield assertTrue(r.left.exists(m => m.contains("Int => String") && m.contains("Int => Int")))
    },
    test("compile error: handler arity doesn't match method arity") {
      for r <- typeCheck(
          "slayer.stub[slayer.StubHandlerSpec.Repo](_.merge(slayer.any, slayer.any, slayer.any))((a: Int, b: Int) => a + b)"
        )
      yield assertTrue(r.left.exists(m => m.contains("(Int, Int, Int) => Int") && m.contains("(Int, Int) => Int")))
    },
    test("compile error: handler param type doesn't accept method param") {
      for r <- typeCheck("slayer.stub[slayer.StubHandlerSpec.Repo](_.find(slayer.any))((s: String) => \"x\")")
      yield assertTrue(r.isLeft)
    },
  )
end StubHandlerSpec
